package com.solo4.calendarreminder.calendar.di.modules.eventdetails

import com.solo4.calendarreminder.calendar.data.repository.eventdetails.EventDetailsRepository
import com.solo4.calendarreminder.calendar.presentation.eventdetails.EventDetailsComponent
import com.solo4.calendarreminder.calendar.presentation.eventdetails.content.EventDetailsViewModel
import org.koin.dsl.module

val eventDetailsModule = module {
    scope<EventDetailsComponent> {
        // data
        scoped { EventDetailsRepository(get()) }

        // domain

        // presentation
        scoped { params -> EventDetailsViewModel(params.get(), get()) }
    }
}