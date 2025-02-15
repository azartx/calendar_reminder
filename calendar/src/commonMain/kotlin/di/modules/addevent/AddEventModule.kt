package com.solo4.calendarreminder.calendar.di.modules.addevent

import com.solo4.calendarreminder.calendar.data.repository.addevent.AddEventRepository
import com.solo4.calendarreminder.calendar.presentation.addevent.AddEventComponent
import com.solo4.calendarreminder.calendar.presentation.addevent.content.AddEventViewModel
import com.solo4.calendarreminder.calendar.presentation.addevent.content.model.AddEventDayParam
import com.solo4.calendarreminder.data.mapper.CalendarEventMapper
import org.koin.dsl.module

val addEventModule = module {
    scope<AddEventComponent> {
        // data
        scoped { AddEventRepository(get(), get()) }
        scoped { CalendarEventMapper() }

        // domain

        // presentation
        scoped { params -> AddEventViewModel(get(), get(), params.get<AddEventDayParam>().dayId) }
    }
}