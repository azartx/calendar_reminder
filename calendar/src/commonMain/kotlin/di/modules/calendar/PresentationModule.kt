package com.solo4.calendarreminder.calendar.di.modules.calendar

import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.nodes.calendar.content.factory.CalendarModelFactory
import org.koin.dsl.module

val presentationModule = module {
    factory { CalendarViewModel(get(), get(), get() ,get()) }
    factory { CalendarModelFactory(get()) }
}