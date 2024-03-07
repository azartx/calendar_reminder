package com.solo4.calendarreminder.presentation.screens.eventdetails

import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.data.model.CalendarEvent
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.navigation.Route.ArgKeys.EVENT_DETAILS

class EventDetailsViewModel : ViewModel() {

    val event = ArgumentHolder.getArg<CalendarEvent>(Route.EventDetailsScreenRoute, EVENT_DETAILS)
}