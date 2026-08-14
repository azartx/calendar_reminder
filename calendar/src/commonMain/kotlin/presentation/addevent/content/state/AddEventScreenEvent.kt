package com.solo4.calendarreminder.calendar.presentation.addevent.content.state

import com.solo4.core.calendar.model.Millis
import com.solo4.core.mvi.screenevent.ScreenEvent

sealed interface AddEventScreenEvent : ScreenEvent<AddEventScreenState> {

    data class OnTitleTextChanged(val text: String) : AddEventScreenEvent
    data class OnDescriptionTextChanged(val text: String) : AddEventScreenEvent
    data class OnDatePickerButtonPressed(val showOnlyTimePicker: Boolean) : AddEventScreenEvent
    data object OnDismissDatePickerClicked : AddEventScreenEvent
    data class OnDateOnlySelected(val selectedDate: String) : AddEventScreenEvent
    data class OnTimePickerDismissed(val selectedDate: String) : AddEventScreenEvent
    data class OnTimeEnabledChanged(val isEnabled: Boolean, val selectedDate: String) : AddEventScreenEvent
    data class OnNotificationEnabledChanged(val isEnabled: Boolean) : AddEventScreenEvent
    data class OnSchedulingFilterChipClicked(val millis: Millis) : AddEventScreenEvent
}
