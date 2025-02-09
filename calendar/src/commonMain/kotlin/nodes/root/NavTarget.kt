package com.solo4.calendarreminder.calendar.nodes.root

sealed class NavTarget  {
    //@Parcelize()
    data object CalendarScreen : NavTarget()

   // @Parcelize()
    data class DayDetailsScreen(val dayId: Long) : NavTarget()

  //  @Parcelize()
    data object EventDetailsScreen : NavTarget()

    data class AddEventScreen(val concreteDay: Long?) : NavTarget()
}