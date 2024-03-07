package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable

@Stable
sealed interface Route {
    data object CalendarScreenRoute : Route
    data object AddEventScreenRoute : Route
    data object DayDetailsScreenRoute : Route
    data object EventDetailsScreenRoute : Route

    data object Back : Route

    companion object ArgKeys {
        const val DAY_ID = "dayId"
        const val EVENT_DETAILS = "eventDetails"
    }
}