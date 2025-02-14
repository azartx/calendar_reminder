package com.solo4.calendarreminder.calendar.di.modules.calendar

import org.koin.dsl.module

val calendarModule = module {
    includes(dataModule, domainModule, presentationModule)
}