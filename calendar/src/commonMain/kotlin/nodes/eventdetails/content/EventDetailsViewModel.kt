package com.solo4.calendarreminder.calendar.nodes.eventdetails.content

import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.mvi.decompose.ViewModel

class EventDetailsViewModel(
    val event: CalendarEvent
) : ViewModel()