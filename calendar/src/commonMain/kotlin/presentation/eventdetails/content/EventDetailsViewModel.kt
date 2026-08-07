package com.solo4.calendarreminder.calendar.presentation.eventdetails.content

import com.solo4.calendarreminder.calendar.data.repository.eventdetails.EventDetailsRepository
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.mvi.decompose.ViewModel
import com.solo4.domain.eventmanager.EventsNotificationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventDetailsViewModel(
    private val baseEvent: CalendarEvent,
    private val eventDetailsRepository: EventDetailsRepository,
    private val eventsNotificationManager: EventsNotificationManager,
) : ViewModel() {

    // TODO implement errors after save the changed event
    private val _calendarEventState = MutableStateFlow(baseEvent)
    val calendarEventState: StateFlow<CalendarEvent> = _calendarEventState.asStateFlow()

    private var onBackListener = {}

    // TODO change to mvi
    fun registerOnBackEvent(callback: () -> Unit) {
        onBackListener = callback
    }

    fun removeEvent() {
        viewModelScope.launch {
            eventsNotificationManager.cancelEvent(baseEvent.eventId)
            eventDetailsRepository.removeEvent(baseEvent.eventId)
            onBackListener.invoke()
            onBackListener = {}
        }
    }

    fun updateEvent(calendarEvent: CalendarEvent) {
        _calendarEventState.tryEmit(calendarEvent)
    }

    fun saveEventChanges(calendarEvent: CalendarEvent) {
        viewModelScope.launch {
            eventDetailsRepository.saveEvent(calendarEvent)
            eventsNotificationManager.rescheduleEvent(
                calendarEvent,
                calendarEvent.scheduleBeforeMillis
            )
        }
    }
}
