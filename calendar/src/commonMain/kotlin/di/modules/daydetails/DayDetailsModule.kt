package com.solo4.calendarreminder.calendar.di.modules.daydetails

import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.data.repository.daydetails.DayDetailsRepository
import com.solo4.calendarreminder.calendar.presentation.daydetails.DayDetailsComponent
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.DayDetailsViewModel
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.model.DayIdParam
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val dayDetailsModule = module {
    scope<DayDetailsComponent> {
        // data
        scoped { CalendarEventMapper() }
        scoped { CalendarRepository(get(), get()) }
        scoped { DayDetailsRepository(get()) }

        // domain

        // presentation
        scoped { params -> DayDetailsViewModel(get(), get(), get(), params.get<DayIdParam>().dayId) }
    }
}