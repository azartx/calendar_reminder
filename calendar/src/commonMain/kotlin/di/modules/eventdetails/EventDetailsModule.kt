package com.solo4.calendarreminder.calendar.di.modules.eventdetails

import com.solo4.calendarreminder.calendar.data.repository.eventdetails.EventDetailsRepository
import com.solo4.calendarreminder.calendar.presentation.eventdetails.EventDetailsComponent
import com.solo4.calendarreminder.calendar.presentation.eventdetails.content.EventDetailsViewModel
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val eventDetailsModule = module {
    scope<EventDetailsComponent> {
        // data
        scoped { EventDetailsRepository(get(), get()) }

        // domain
        scoped { CalendarEventMapper() }

        // presentation
        scoped { params -> EventDetailsViewModel(params.get(), get()) }
    }
}