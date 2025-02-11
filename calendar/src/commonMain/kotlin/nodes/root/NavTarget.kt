package com.solo4.calendarreminder.calendar.nodes.root

import com.solo4.core.calendar.model.CalendarEvent

sealed class NavTarget  {
    //@Parcelize()
    data object CalendarScreen : NavTarget()

   // @Parcelize()
    data class DayDetailsScreen(val dayId: Long) : NavTarget()

  //  @Parcelize()
    data class EventDetailsScreen(val event: CalendarEvent) : NavTarget()

    data class AddEventScreen(val concreteDay: Long?) : NavTarget()
}