package com.solo4.calendarreminder.presentation.screens.calendar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel
import com.solo4.calendarreminder.presentation.screens.calendar.factory.CalendarModelFactory
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentMonth
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalendarViewModel(
    private val calendarRepository: CalendarRepository = CalendarRepository(
        eventsDao = CalendarEventsDatabase.instance.eventsDao,
        calendarEventMapper = CalendarEventMapper()
    )
) : ViewModel() {

    private val calendarFactory: CalendarModelFactory = CalendarModelFactory()

    private val _calendarModel = MutableStateFlow(calendarFactory.createMonthModels(currentYear, currentMonth))
    val calendarModel = _calendarModel.asStateFlow()

    private lateinit var monthEvents: List<CalendarEvent>

    init {
        viewModelScope.launch {
            monthEvents = _calendarModel.value
                .rows
                .flatMap { row ->
                    row.rowItems
                        .flatMap { calendarRepository.getMonthEvents(it.dayId) }
                }
        }
    }
    fun onDayClicked(appCalendarItemModel: AppCalendarItemModel) {
        if (::monthEvents.isInitialized) {
            val eventsStr = monthEvents.filter { it.dayMillis == appCalendarItemModel.dayId }.joinToString("\n")
            Log.e("Events_${appCalendarItemModel.dayId}", eventsStr)
        }
    }
}