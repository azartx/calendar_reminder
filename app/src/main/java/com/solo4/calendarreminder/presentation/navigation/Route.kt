package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable
import com.solo4.calendarreminder.data.model.CalendarEvent

@Stable
sealed interface Route {
    data object CalendarScreenRoute : Route
    data object AddEventScreenRoute : Route
    data object DayDetailsScreenRoute : Route
    data object EventDetailsScreenRoute : Route

    data object Back : Route
}

interface RouteArgs<R : Route>
object EmptyArgs : RouteArgs<Nothing>

data class AddEventScreenArgs(
    val concreteDayId: Long
) : RouteArgs<Route.AddEventScreenRoute>

data class EventDetailsScreenArgs(
    val event: CalendarEvent
) : RouteArgs<Route.EventDetailsScreenRoute>

data class DayDetailsScreenArgs(
    val dayId: Long
) : RouteArgs<Route.DayDetailsScreenRoute>