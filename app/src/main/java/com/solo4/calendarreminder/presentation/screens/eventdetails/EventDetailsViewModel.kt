package com.solo4.calendarreminder.presentation.screens.eventdetails

import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.presentation.navigation.ArgumentHolder
import com.solo4.calendarreminder.presentation.navigation.EventDetailsScreenArgs
import com.solo4.calendarreminder.presentation.navigation.Route

class EventDetailsViewModel : ViewModel() {

    val event = ArgumentHolder.getArg<
            Route.EventDetailsScreenRoute,
            EventDetailsScreenArgs
            >(Route.EventDetailsScreenRoute)
        .event
}