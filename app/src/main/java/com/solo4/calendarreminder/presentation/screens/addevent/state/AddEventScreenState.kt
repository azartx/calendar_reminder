package com.solo4.calendarreminder.presentation.screens.addevent.state

import com.solo4.calendarreminder.core.mvi.screenstate.ScreenState
import com.solo4.calendarreminder.data.utils.Millis

data class AddEventScreenState(
    val title: String = "",
    val description: String = "",
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val selectedDate: String = "Date is not selected",
    val selectedScheduleBeforeMillis: Millis = Millis.MINUTES_15
) : ScreenState