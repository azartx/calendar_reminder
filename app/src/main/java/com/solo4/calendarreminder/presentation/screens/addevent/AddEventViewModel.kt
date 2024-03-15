package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.presentation.navigation.AddEventScreenArgs
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenState
import com.solo4.calendarreminder.presentation.screens.calendar.utils.DATE_PATTERN
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
    private val calendar: CalendarWrapper = App.calendarWrapper
) : ViewModel() {

    val concreteDay: Long? = ArgumentHolder.getArgOrNull<
            Route.AddEventScreenRoute,
            AddEventScreenArgs
            >(Route.AddEventScreenRoute)
        ?.concreteDayId

    private val _datePickerState = MutableStateFlow(
        DatePickerState(
            Locale.getDefault(),
            initialSelectedDateMillis = concreteDay
                ?.let { calendar.addTimezoneOffset(calendar.formatDateIdToDayMillis(it)) }
        )
    )
    val datePickerState = _datePickerState.asStateFlow()

    private val _timePickerState = MutableStateFlow(TimePickerState(0, 0, true))
    val timePickerState = _timePickerState.asStateFlow()

    private val _screenState = MutableStateFlow(
        AddEventScreenState().run {
            if (concreteDay == null) this else copy(
                selectedDate = getDateFromPicker().toDateByPattern(DATE_PATTERN)
            )
        }
    )
    val screenState = _screenState.asStateFlow()

    private val _navigationState = MutableSharedFlow<Route>()
    val navigationState = _navigationState.asSharedFlow()

    fun onTitleTextFieldChanged(value: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                title = value
            )
        )
    }

    fun onDescriptionTextFieldChanged(value: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                description = value
            )
        )
    }

    fun onDatePickerButtonPressed() {
        _screenState.tryEmit(
            _screenState.value.copy(
                // Показываем дэйт пикер если нет предустановленного дня
                isDatePickerVisible = concreteDay == null,
                // Показываем тайм пикер, если ден был заранее установлен
                isTimePickerVisible = concreteDay != null
            )
        )
    }

    fun onDismissDatePickerClicked() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = true
            )
        )
    }

    fun onTimePickerDismissed() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = false,
                selectedDate = (getDateFromPicker() + timePickerState.value.millis).toDateByPattern()
            )
        )
    }

    fun onSubmitButtonClicked() {
        viewModelScope.launch {
            // todo emit loading state

            if (timePickerState.value.millis == 0L) throw Exception("Should be validated")

            val eventDate = getDateFromPicker()
            val eventTimeMillis = eventDate + timePickerState.value.millis

            val data = _screenState.value
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

            App.eventsNotificationManager.scheduleCalendarEvent(event)

            _navigationState.emit(Route.Back)
        }
    }

    private fun getDateFromPicker(): Long {
        return datePickerState.value.selectedDateMillis?.let(calendar::removeTimezoneOffset)!!
    }
}