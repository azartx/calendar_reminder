package com.solo4.calendarreminder.presentation.screens.addevent.state

data class AddEventScreenState(
    val title: String = "",
    val description: String = "",
    val isDatePickerVisible: Boolean = false,
    val selectedDate: String = "Date is not selected"
)
