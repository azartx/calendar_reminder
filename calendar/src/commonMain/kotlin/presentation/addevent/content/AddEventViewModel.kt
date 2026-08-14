package com.solo4.calendarreminder.calendar.presentation.addevent.content

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.solo4.calendarreminder.calendar.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventErrorState
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventScreenEvent
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventScreenState
import com.solo4.calendarreminder.calendar.presentation.addevent.content.state.AddEventScreenStateDelegate
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.DATE_PATTERN
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.addTimezoneOffset
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.getFormattedDateId
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.removeTimezoneOffset
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.calendar.utils.getDefaultLocale
import com.solo4.core.mvi.screenstate.ScreenStateDelegate
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.calendar.model.Millis
import com.solo4.core.mvi.decompose.ViewModel
import com.solo4.domain.eventmanager.EventsNotificationManager
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel(
    private val addEventRepository: AddEventRepository,
    private val calendar: CalendarWrapper,
    private val eventsNotificationManager: EventsNotificationManager,
    private val concreteDay: Long?
) : ViewModel(),
    ScreenStateDelegate<AddEventScreenState, AddEventErrorState, AddEventScreenEvent> by AddEventScreenStateDelegate(
        initialSelectedDate = if (concreteDay == null)
            calendar.millisNow.toDateByPattern() else calendar.addTimezoneOffset(
            calendar.formatDateIdToDayMillis(concreteDay)
        ).toDateByPattern()
    ) {

    val scheduleBeforeMillis = Millis.entries
        .filter { it != Millis.NONE && it.toMinutes() > 0L }
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

    private val _navigationState = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
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
            if (!screenState.value.isTimeEnabled) {
                handleEvent(AddEventScreenEvent.OnDateOnlySelected(formattedSelectedDate(isTimeEnabled = false)))
            } else {
                handleEvent(AddEventScreenEvent.OnDismissDatePickerClicked)
            }
        }
    }

    fun onTimePickerDismissed() {
        viewModelScope.launch {
            handleEvent(
                AddEventScreenEvent.OnTimePickerDismissed(
                    selectedDate = formattedSelectedDate(isTimeEnabled = true)
                )
            )
        }
    }

    fun onTimeEnabledChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            handleEvent(
                AddEventScreenEvent.OnTimeEnabledChanged(
                    isEnabled = isEnabled,
                    selectedDate = formattedSelectedDate(isTimeEnabled = isEnabled)
                )
            )
        }
    }

    fun onNotificationEnabledChanged(isEnabled: Boolean) {
        viewModelScope.launch {
            if (isEnabled && !screenState.value.isTimeEnabled) {
                handleEvent(
                    AddEventScreenEvent.OnTimeEnabledChanged(
                        isEnabled = true,
                        selectedDate = formattedSelectedDate(isTimeEnabled = true)
                    )
                )
            }
            handleEvent(AddEventScreenEvent.OnNotificationEnabledChanged(isEnabled))
        }
    }

    fun onSchedulingFilterChipClicked(millis: Millis) {
        viewModelScope.launch {
            handleEvent(AddEventScreenEvent.OnSchedulingFilterChipClicked(millis))
        }
    }

    fun onSubmitButtonClicked() {
        viewModelScope.launch {
            val eventDate = getDateFromPicker()
            val data = screenState.value

            if (!errorDelegate.isScreenStateValid(data)) return@launch

            val eventTimeMillis = if (data.isTimeEnabled) {
                eventDate + timePickerState.value.millis
            } else {
                0L
            }
            val shouldNotify = data.isNotificationEnabled && data.isTimeEnabled
            val scheduleBeforeMillis = if (shouldNotify) {
                data.selectedScheduleBeforeMillis.millis
            } else {
                Millis.NONE.millis
            }
            val event = CalendarEvent(
                dayMillis = getFormattedDateId(
                    day = calendar.dayOfMonthOf(eventDate),
                    month = calendar.monthOf(eventDate),
                    year = calendar.yearOf(eventDate)
                ),
                title = data.title,
                description = data.description,
                eventTimeMillis = eventTimeMillis,
                scheduleBeforeMillis = scheduleBeforeMillis,
            )
            runCatching {
                val eventId = addEventRepository.saveEvent(event)
                val savedEvent = event.copy(eventId = eventId)
                if (shouldNotify) {
                    eventsNotificationManager.scheduleEvent(
                        savedEvent,
                        scheduleBeforeMillis
                    )
                }
            }.onSuccess {
                _navigationState.emit(Unit)
            }
        }
    }

    private fun formattedSelectedDate(isTimeEnabled: Boolean): String {
        val eventDate = getDateFromPicker()
        return if (isTimeEnabled) {
            (eventDate + timePickerState.value.millis).toDateByPattern()
        } else {
            eventDate.toDateByPattern(DATE_PATTERN)
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