package com.solo4.calendarreminder.calendar.nodes.addevent.content

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.calendar.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.calendar.nodes.addevent.content.state.AddEventErrorState
import com.solo4.calendarreminder.calendar.nodes.addevent.content.state.AddEventScreenEvent
import com.solo4.calendarreminder.calendar.nodes.addevent.content.state.AddEventScreenState
import com.solo4.calendarreminder.calendar.nodes.addevent.content.state.AddEventScreenStateDelegate
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.addTimezoneOffset
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.getFormattedDateId
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.removeTimezoneOffset
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.calendar.utils.getDefaultLocale
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.mvi.screenstate.ScreenStateDelegate
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.calendar.model.Millis
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel(
    private val addEventRepository: AddEventRepository = AddEventRepository(
        eventsDao = CalendarEventsDatabase.InstanceHolder.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    ),
    private val calendar: CalendarWrapper = getPlatformCalendar(),
    private val concreteDay: Long?
) : ViewModel(),
    ScreenStateDelegate<AddEventScreenState, AddEventErrorState, AddEventScreenEvent> by AddEventScreenStateDelegate(
        initialSelectedDate = if (concreteDay == null)
            calendar.millisNow.toDateByPattern() else calendar.addTimezoneOffset(
            calendar.formatDateIdToDayMillis(concreteDay)
        ).toDateByPattern()
    ) {

    val scheduleBeforeMillis = Millis.entries
        .filter {
            if (it == Millis.NONE) return@filter true
            if (it.toMinutes() == 0L) return@filter false
            true
        }
        .sortedBy { it.millis }

    private val _datePickerState = MutableStateFlow(
        DatePickerState(
            getDefaultLocale(),
            initialSelectedDateMillis = concreteDay
                ?.let { calendar.addTimezoneOffset(calendar.formatDateIdToDayMillis(it)) }
                ?: calendar.addTimezoneOffset(calendar.millisNow)
        )
    )
    val datePickerState = _datePickerState.asStateFlow()

    private val _timePickerState = MutableStateFlow(TimePickerState(0, 0, true))
    val timePickerState = _timePickerState.asStateFlow()

    private val _navigationState = MutableSharedFlow<Unit>()
    val navigationState = _navigationState.asSharedFlow()

    fun onTitleTextFieldChanged(value: String) {
        viewModelScope.launch {
            handleEvent(AddEventScreenEvent.OnTitleTextChanged(value))
        }
    }

    fun onDescriptionTextFieldChanged(value: String) {
        viewModelScope.launch {
            handleEvent(AddEventScreenEvent.OnDescriptionTextChanged(value))
        }
    }

    fun onDatePickerButtonPressed() {
        viewModelScope.launch {
            handleEvent(
                AddEventScreenEvent.OnDatePickerButtonPressed(showOnlyTimePicker = concreteDay != null)
            )
        }
    }

    fun onDismissDatePickerClicked() {
        viewModelScope.launch {
            handleEvent(AddEventScreenEvent.OnDismissDatePickerClicked)
        }
    }

    fun onTimePickerDismissed() {
        viewModelScope.launch {
            handleEvent(
                AddEventScreenEvent.OnTimePickerDismissed(
                    selectedDate = (getDateFromPicker() + timePickerState.value.millis).toDateByPattern()
                )
            )
        }
    }

    fun onSchedulingFilterChipClicked(millis: Millis) {
        viewModelScope.launch {
            handleEvent(AddEventScreenEvent.OnSchedulingFilterChipClicked(millis))
        }
    }

    fun onSubmitButtonClicked() {
        viewModelScope.launch {
            // todo emit loading state

            val eventDate = getDateFromPicker()
            val eventTimeMillis = eventDate + timePickerState.value.millis

            val data = screenState.value

            if (!errorDelegate.isScreenStateValid(data)) return@launch

            val event = CalendarEvent(
                dayMillis = getFormattedDateId(
                    day = calendar.dayOfMonthOf(eventDate),
                    month = calendar.monthOf(eventDate),
                    year = calendar.yearOf(eventDate)
                ),
                title = data.title,
                description = data.description,
                eventTimeMillis = eventTimeMillis
            )
            addEventRepository.saveEvent(event)

            // TODO post event to notification manager
            /*App.eventsNotificationManager.scheduleCalendarEvent(
                event,
                data.selectedScheduleBeforeMillis.millis
            )*/

            _navigationState.emit(Unit)
        }
    }

    private fun getDateFromPicker(): Long {
        return datePickerState.value.selectedDateMillis?.let(calendar::removeTimezoneOffset) ?: 0
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private val TimePickerState.millis: Long
    get() {
        val hourMillis = hour * Millis.HOUR_1.millis
        val minutesMillis = minute * Millis.MINUTES_1.millis

        return hourMillis + minutesMillis
    }