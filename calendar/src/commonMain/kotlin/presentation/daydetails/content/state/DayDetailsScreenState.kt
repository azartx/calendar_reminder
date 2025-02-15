package com.solo4.calendarreminder.calendar.presentation.daydetails.content.state

import com.solo4.core.calendar.model.CalendarEvent

sealed interface DayDetailsScreenState {

    data object Loading : DayDetailsScreenState

    data class Content(val dayEvents: List<CalendarEvent>) : DayDetailsScreenState
}