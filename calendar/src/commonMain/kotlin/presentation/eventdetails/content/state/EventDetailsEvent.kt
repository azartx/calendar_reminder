package com.solo4.calendarreminder.calendar.presentation.eventdetails.content.state

sealed interface EventDetailsEvent {

    data object Back : EventDetailsEvent
}
