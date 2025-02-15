package com.solo4.calendarreminder.calendar.di.modules.eventdetails

import com.solo4.calendarreminder.calendar.nodes.daydetails.DayDetailsComponent
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsViewModel
import org.koin.dsl.module

val eventDetailsModule = module {
    scope<DayDetailsComponent> {
        // data

        // domain

        // presentation
        scoped { params -> EventDetailsViewModel(params.get()) }
    }
}