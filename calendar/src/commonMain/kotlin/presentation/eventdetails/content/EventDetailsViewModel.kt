package com.solo4.calendarreminder.calendar.presentation.eventdetails.content

import com.solo4.calendarreminder.calendar.data.repository.eventdetails.EventDetailsRepository
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.mvi.decompose.ViewModel
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    val event: CalendarEvent,
    private val eventDetailsRepository: EventDetailsRepository,
) : ViewModel() {

    fun removeEvent() {
        viewModelScope.launch {
            eventDetailsRepository.removeEvent(event.eventId)
        }
    }
}