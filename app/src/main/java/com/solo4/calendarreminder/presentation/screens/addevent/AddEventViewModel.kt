package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.core.mvi.screenstate.ScreenStateDelegate
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.data.utils.Millis
import com.solo4.calendarreminder.presentation.navigation.AddEventScreenArgs
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventErrorState
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenEvent
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenState
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenStateDelegate
import com.solo4.calendarreminder.presentation.screens.calendar.utils.addTimezoneOffset
import com.solo4.calendarreminder.presentation.screens.calendar.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.presentation.screens.calendar.utils.getFormattedDateId
import com.solo4.calendarreminder.presentation.screens.calendar.utils.removeTimezoneOffset
import com.solo4.calendarreminder.presentation.screens.calendar.utils.toDateByPattern
import com.solo4.calendarreminder.utils.calendar.CalendarWrapper
import com.solo4.calendarreminder.utils.millis
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel(
    private val addEventRepository: AddEventRepository = AddEventRepository(
        eventsDao = CalendarEventsDatabase.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    ),
    private val calendar: CalendarWrapper = App.calendarWrapper,
    private val concreteDay: Long? = ArgumentHolder.getArgOrNull<
        Route.AddEventScreenRoute,
        AddEventScreenArgs
        >(Route.AddEventScreenRoute)
        ?.concreteDayId
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
            Locale.getDefault(),
            initialSelectedDateMillis = concreteDay
                ?.let { calendar.addTimezoneOffset(calendar.formatDateIdToDayMillis(it)) }
                ?: calendar.addTimezoneOffset(calendar.millisNow)
        )
    )
    val datePickerState = _datePickerState.asStateFlow()

    private val _timePickerState = MutableStateFlow(TimePickerState(0, 0, true))
    val timePickerState = _timePickerState.asStateFlow()

    private val _navigationState = MutableSharedFlow<Route>()
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

            App.eventsNotificationManager.scheduleCalendarEvent(
                event,
                data.selectedScheduleBeforeMillis.millis
            )

            _navigationState.emit(Route.Back)
        }
    }

    private fun getDateFromPicker(): Long {
        return datePickerState.value.selectedDateMillis?.let(calendar::removeTimezoneOffset) ?: 0
    }
}