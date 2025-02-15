package com.solo4.calendarreminder.calendar.di

import com.solo4.calendarreminder.calendar.di.modules.addevent.addEventModule
import com.solo4.calendarreminder.calendar.di.modules.calendar.calendarModule
import com.solo4.calendarreminder.calendar.di.modules.daydetails.dayDetailsModule
import com.solo4.calendarreminder.calendar.di.modules.eventdetails.eventDetailsModule
import com.solo4.calendarreminder.calendar.di.modules.sharedModule
import com.solo4.core.kmputils.MultiplatformContext
import org.koin.core.KoinApplication
import org.koin.dsl.module

fun KoinApplication.applyApplicationModules(context: MultiplatformContext) {
    modules(
        listOf(
            module { single { context } },
            sharedModule,
            calendarModule,
            dayDetailsModule,
            eventDetailsModule,
            addEventModule,
        )
    )
}