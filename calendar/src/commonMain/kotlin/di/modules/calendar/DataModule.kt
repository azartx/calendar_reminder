package com.solo4.calendarreminder.calendar.di.modules.calendar

import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val dataModule = module {
    factory { CalendarRepository(get(), get()) }
    factory { CalendarEventMapper() }
    factory { CalendarEventsDatabase.InstanceHolder.instance.eventsDao }
}