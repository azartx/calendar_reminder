package com.solo4.calendarreminder.calendar.nodes.root

import com.solo4.core.calendar.model.CalendarEvent
import kotlinx.serialization.Serializable

@Serializable
sealed class NavTarget  {

    @Serializable
    data object CalendarScreen : NavTarget()

    @Serializable
    data class DayDetailsScreen(val dayId: Long) : NavTarget()

    @Serializable
    data class EventDetailsScreen(val event: CalendarEvent) : NavTarget()

    @Serializable
    data class AddEventScreen(val concreteDay: Long?) : NavTarget()
}