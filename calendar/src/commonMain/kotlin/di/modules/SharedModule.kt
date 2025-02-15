package com.solo4.calendarreminder.calendar.di.modules

import com.solo4.calendarreminder.data.database.CalendarEventsDatabase
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.domain.eventmanager.getEventsNotificationManager
import org.koin.dsl.module

val sharedModule = module {
    single { getPlatformCalendar() }
    factory { CalendarEventsDatabase.InstanceHolder.instance.eventsDao }
    single { getEventsNotificationManager(get(), get()) }
}