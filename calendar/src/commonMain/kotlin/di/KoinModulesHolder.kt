package com.solo4.calendarreminder.calendar.di

import com.solo4.calendarreminder.calendar.di.modules.calendar.calendarModule
import com.solo4.calendarreminder.calendar.di.modules.sharedModule
import org.koin.core.module.Module

val applicationModules: List<Module> get() = listOf(
    sharedModule,
    calendarModule,
)