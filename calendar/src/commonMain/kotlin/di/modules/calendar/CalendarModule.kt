package com.solo4.calendarreminder.calendar.di.modules.calendar

import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.calendar.CalendarComponent
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.nodes.calendar.content.factory.CalendarModelFactory
import com.solo4.calendarreminder.calendar.nodes.calendar.content.mapper.CalendarItemMapper
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val calendarModule = module {
    scope<CalendarComponent> {
        //data
        scoped { CalendarRepository(get(), get()) }
        scoped { CalendarEventMapper() }

        // domain
        scoped { CalendarItemMapper() }

        // presentation
        scoped { CalendarViewModel(get(), get(), get() ,get()) }
        scoped { CalendarModelFactory(get()) }
    }
}