package com.solo4.calendarreminder.calendar.di.modules.daydetails

import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.daydetails.DayDetailsComponent
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.DayDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.model.DayIdParam
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val dayDetailsModule = module {
    scope<DayDetailsComponent> {
        // data
        scoped { CalendarEventMapper() }
        scoped { CalendarRepository(get(), get()) }

        // domain

        // presentation
        scoped { params -> DayDetailsViewModel(get(), get(), params.get<DayIdParam>().dayId) }
    }
}