package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.temporal.ChronoField
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel(
    private val addEventRepository: AddEventRepository = AddEventRepository(
        eventsDao = CalendarEventsDatabase.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    )
) : ViewModel() {

    private val _datePickerState = MutableStateFlow(DatePickerState(Locale.getDefault()))
    val datePickerState = _datePickerState.asStateFlow()

    private val _screenState = MutableStateFlow(AddEventScreenState())
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
                isDatePickerVisible = true
            )
        )
    }

    fun onDismissDatePickerClicked() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                selectedDate = getFormatTimeWithTZ(Date(datePickerState.value.selectedDateMillis ?: 0))
            )
        )
    }

    fun onSubmitButtonClicked() {
        viewModelScope.launch {
            // todo emit loading state

            val data = _screenState.value
            val event = CalendarEvent(
                dayMillis = TimeUnit.MILLISECONDS.toDays(datePickerState.value.selectedDateMillis!!),
                title = data.title,
                description = data.description,
                eventTimeMillis = datePickerState.value.selectedDateMillis!!
            )
            addEventRepository.saveEvent(event)

            _navigationState.emit(Route.Back)
        }
    }

    private fun getFormatTimeWithTZ(currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return timeZoneDate.format(currentTime);
    }
}