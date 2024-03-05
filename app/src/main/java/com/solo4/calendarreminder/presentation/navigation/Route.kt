package com.solo4.calendarreminder.presentation.navigation

sealed interface Route {
    data object CalendarScreenRoute : Route
    data object AddEventScreenRoute : Route

    data object Back : Route
}