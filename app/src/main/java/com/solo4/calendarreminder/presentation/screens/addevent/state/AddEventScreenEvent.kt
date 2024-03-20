package com.solo4.calendarreminder.presentation.screens.addevent.state

import com.solo4.calendarreminder.core.mvi.screenevent.ScreenEvent
import com.solo4.calendarreminder.data.utils.Millis

sealed interface AddEventScreenEvent : ScreenEvent<AddEventScreenState> {

    data class OnTitleTextChanged(val text: String) : AddEventScreenEvent
    data class OnDescriptionTextChanged(val text: String) : AddEventScreenEvent
    data class OnDatePickerButtonPressed(val showOnlyTimePicker: Boolean) : AddEventScreenEvent
    data object OnDismissDatePickerClicked : AddEventScreenEvent
    data class OnTimePickerDismissed(val selectedDate: String) : AddEventScreenEvent
    data class OnSchedulingFilterChipClicked(val millis: Millis) : AddEventScreenEvent
}