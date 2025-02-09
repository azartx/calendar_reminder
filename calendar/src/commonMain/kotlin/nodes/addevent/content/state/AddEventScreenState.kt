package com.solo4.calendarreminder.calendar.nodes.addevent.content.state

import com.solo4.core.calendar.model.Millis
import com.solo4.core.mvi.screenstate.ScreenState

data class AddEventScreenState(
    val title: String = "",
    val description: String = "",
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val selectedDate: String = "Date is not selected",
    val selectedScheduleBeforeMillis: Millis = Millis.MINUTES_15
) : ScreenState