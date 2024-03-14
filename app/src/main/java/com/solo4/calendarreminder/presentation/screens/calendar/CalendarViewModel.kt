package com.solo4.calendarreminder.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.App
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.presentation.screens.calendar.factory.CalendarModelFactory
import com.solo4.calendarreminder.presentation.screens.calendar.mapper.CalendarItemMapper
import com.solo4.calendarreminder.utils.calendar.CalendarWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val calendarRepository: CalendarRepository = CalendarRepository(
        eventsDao = CalendarEventsDatabase.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    ),
    private val calendarItemMapper: CalendarItemMapper = CalendarItemMapper(),
    private val calendarFactory: CalendarModelFactory = CalendarModelFactory(),
    calendar: CalendarWrapper = App.calendarWrapper
) : ViewModel() {

    private var calendarDate = calendar.yearOf(calendar.millisNow) to
            calendar.monthOf(calendar.millisNow)

    private val _calendarModel = MutableStateFlow(
        calendarFactory.createMonthModels(calendarDate.first, calendarDate.second)
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

    fun onCalendarSwiped(rightSwipe: Boolean) {
        viewModelScope.launch {
            if (rightSwipe) loadPreviousCalendar() else loadNextCalendar()

            updateDaysEventsState()
        }
    }

    private suspend fun loadNextCalendar() {
        calendarDate = incrementYearMonth(calendarDate)
        _calendarModel.emit(
            calendarFactory.createMonthModels(calendarDate.first, calendarDate.second)
        )
    }

    private suspend fun loadPreviousCalendar() {
        calendarDate = decrementYearMonth(calendarDate)
        _calendarModel.emit(
            calendarFactory.createMonthModels(calendarDate.first, calendarDate.second)
        )
    }

    private fun incrementYearMonth(yearMonth: Pair<Int, Int>): Pair<Int, Int> {
        var newYear = yearMonth.first
        val newMonth = (yearMonth.second + 1).takeIf { it <= 12 } ?: run {
            newYear += 1
            1
        }
        return Pair(newYear, newMonth)
    }

    private fun decrementYearMonth(yearMonth: Pair<Int, Int>): Pair<Int, Int> {
        var newYear = yearMonth.first
        val newMonth = (yearMonth.second - 1).takeIf { it >= 1 } ?: run {
            newYear -= 1
            12
        }
        return Pair(newYear, newMonth)
    }
}