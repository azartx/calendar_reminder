package com.solo4.calendarreminder.presentation.screens.daydetails

import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.DayDetailsScreenArgs
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.screens.calendar.utils.DATE_PATTERN
import com.solo4.calendarreminder.presentation.screens.calendar.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.presentation.screens.calendar.utils.toDateByPattern
import com.solo4.calendarreminder.presentation.screens.daydetails.state.DayDetailsScreenState
import com.solo4.calendarreminder.utils.calendar.CalendarWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DayDetailsViewModel(
    private val repository: CalendarRepository = CalendarRepository(
        CalendarEventsDatabase.instance.eventsDao,
        CalendarEventMapper()
    ),
    calendar: CalendarWrapper = App.calendarWrapper
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<DayDetailsScreenState>(DayDetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    val dayId = ArgumentHolder.getArg<
            Route.DayDetailsScreenRoute,
            DayDetailsScreenArgs
            >(Route.DayDetailsScreenRoute)
        .dayId

    val currentDate = calendar.formatDateIdToDayMillis(dayId)
        .toDateByPattern(DATE_PATTERN)

    private suspend fun loadDayEvents() {
        _screenState.emit(DayDetailsScreenState.Content(repository.getMonthEvents(dayId)))
    }

    suspend fun onScreenResumed() {
        loadDayEvents()
    }
}