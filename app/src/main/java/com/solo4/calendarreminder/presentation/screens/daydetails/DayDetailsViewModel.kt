package com.solo4.calendarreminder.presentation.screens.daydetails

import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.DayDetailsScreenArgs
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.screens.daydetails.state.DayDetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DayDetailsViewModel(
    private val repository: CalendarRepository = CalendarRepository(
        CalendarEventsDatabase.instance.eventsDao,
        CalendarEventMapper()
    )
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<DayDetailsScreenState>(DayDetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    val dayId = ArgumentHolder.getArg<
            Route.DayDetailsScreenRoute,
            DayDetailsScreenArgs
            >(Route.DayDetailsScreenRoute)
        .dayId

    private suspend fun loadDayEvents() {
        _screenState.emit(DayDetailsScreenState.Content(repository.getMonthEvents(dayId)))
    }

    suspend fun onScreenResumed() {
        loadDayEvents()
    }
}