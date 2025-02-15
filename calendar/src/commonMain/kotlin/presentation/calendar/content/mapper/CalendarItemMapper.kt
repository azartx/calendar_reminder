package com.solo4.calendarreminder.calendar.presentation.calendar.content.mapper

import appcalendar.model.AppCalendarModel

class CalendarItemMapper {

    inline fun updateEventVisibility(
        oldModel: AppCalendarModel,
        hasEventAction: (dayId: Long) -> Boolean
    ): AppCalendarModel {
        return oldModel.copy(
            rows = oldModel.rows.map { row ->
                row.copy(
                    rowItems = row.rowItems.map { item ->
                        item.copy(
                            hasEvents = hasEventAction.invoke(item.dayId)
                        )
                    }
                )
            }
        )
    }
}