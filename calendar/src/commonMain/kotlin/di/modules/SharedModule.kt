package com.solo4.calendarreminder.calendar.di.modules

import com.solo4.calendarreminder.calendar.domain.RestoreRemindersUseCase
import com.solo4.calendarreminder.data.database.CalendarEventsDatabaseHolder
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.domain.eventmanager.getEventsNotificationManager
import org.koin.dsl.module

val sharedModule = module {
    single { getPlatformCalendar() }
    factory { CalendarEventsDatabaseHolder.instance.eventsDao() }
    single { CalendarEventMapper() }
    single { getEventsNotificationManager(get(), get()) }
    single {
        RestoreRemindersUseCase(
            eventsDao = get(),
            mapper = get(),
            eventsNotificationManager = get(),
            calendar = get(),
        )
    }
}
