package com.solo4.calendarreminder.calendar.di.modules.calendar

import com.solo4.calendarreminder.calendar.nodes.calendar.content.mapper.CalendarItemMapper
import org.koin.dsl.module

val domainModule = module {
    factory { CalendarItemMapper() }
}