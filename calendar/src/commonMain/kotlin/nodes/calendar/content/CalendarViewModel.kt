package com.solo4.calendarreminder.calendar.nodes.calendar.content

import com.solo4.core.calendar.CalendarWrapper
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.calendar.content.factory.CalendarModelFactory
import com.solo4.calendarreminder.calendar.nodes.calendar.content.mapper.CalendarItemMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalendarViewModel(
    private val calendarRepository: CalendarRepository,
    private val calendarItemMapper: CalendarItemMapper,
    private val calendarFactory: CalendarModelFactory,
    calendar: CalendarWrapper
) /*: ViewModel()*/ {

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
        /*val updatedItems = calendarItemMapper.updateEventVisibility(_calendarModel.value) {
            calendarRepository.hasDayEvents(it)
        }*/
       // _calendarModel.emit(updatedItems)
    }

    fun onCalendarSwiped(rightSwipe: Boolean) {
        // TODO
        /*viewModelScope.launch {
            if (rightSwipe) loadPreviousCalendar() else loadNextCalendar()

            updateDaysEventsState()
        }*/
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