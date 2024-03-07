package com.solo4.calendarreminder.presentation.screens.daydetails.state

import com.solo4.calendarreminder.data.model.CalendarEvent

sealed interface DayDetailsScreenState {

    data object Loading : DayDetailsScreenState

    data class Content(val dayEvents: List<CalendarEvent>) : DayDetailsScreenState
}