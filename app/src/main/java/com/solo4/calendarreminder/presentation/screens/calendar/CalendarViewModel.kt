package com.solo4.calendarreminder.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.presentation.screens.calendar.factory.CalendarModelFactory
import com.solo4.calendarreminder.presentation.screens.calendar.mapper.CalendarItemMapper
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentMonth
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val calendarRepository: CalendarRepository = CalendarRepository(
        eventsDao = CalendarEventsDatabase.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    ),
    private val calendarItemMapper: CalendarItemMapper = CalendarItemMapper()
) : ViewModel() {

    private val calendarFactory: CalendarModelFactory = CalendarModelFactory()

    private val _calendarModel = MutableStateFlow(
        calendarFactory.createMonthModels(currentYear, currentMonth)
    )
    val calendarModel = _calendarModel.asStateFlow()

    suspend fun onScreenResumed() {
        updateDaysEventsState()
    }

    private suspend fun updateDaysEventsState() {
        val updatedItems = calendarItemMapper.updateEventVisibility(_calendarModel.value) {
            calendarRepository.hasDayEvents(it)
        }
        _calendarModel.emit(updatedItems)
    }
}