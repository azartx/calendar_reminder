package com.solo4.calendarreminder.calendar.presentation.addevent.content.state

import com.solo4.core.calendar.model.Millis
import com.solo4.core.mvi.errorscreenstate.ErrorDelegate
import com.solo4.core.mvi.screenstate.ScreenStateDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEventScreenStateDelegate(
    initialSelectedDate: String
) : ScreenStateDelegate<AddEventScreenState, AddEventErrorState, AddEventScreenEvent> {

    private val _screenState = MutableStateFlow(
        AddEventScreenState(selectedDate = initialSelectedDate)
    )
    override val screenState: StateFlow<AddEventScreenState> = _screenState.asStateFlow()

    override val errorDelegate: ErrorDelegate<AddEventScreenState, AddEventErrorState> = AddEventErrorDelegate()

    override suspend fun handleEvent(event: AddEventScreenEvent) {
        errorDelegate.clearErrors()
        when (event) {
            is AddEventScreenEvent.OnTitleTextChanged -> onTitleChanged(event.text)
            is AddEventScreenEvent.OnDescriptionTextChanged -> onDescriptionChanged(event.text)
            is AddEventScreenEvent.OnDatePickerButtonPressed -> onDatePickerButtonPressed(event.showOnlyTimePicker)
            is AddEventScreenEvent.OnDismissDatePickerClicked -> onDismissDatePickerClicked()
            is AddEventScreenEvent.OnDateOnlySelected -> onDateOnlySelected(event.selectedDate)
            is AddEventScreenEvent.OnTimePickerDismissed -> onTimePickerDismissed(event.selectedDate)
            is AddEventScreenEvent.OnTimeEnabledChanged -> onTimeEnabledChanged(event.isEnabled, event.selectedDate)
            is AddEventScreenEvent.OnNotificationEnabledChanged -> onNotificationEnabledChanged(event.isEnabled)
            is AddEventScreenEvent.OnSchedulingFilterChipClicked -> onSchedulingFilterChipClicked(event.millis)
        }
    }

    private suspend fun onTitleChanged(text: String) {
        _screenState.emit(
            _screenState.value.copy(
                title = text
            )
        )
    }

    private suspend fun onDescriptionChanged(text: String) {
        _screenState.emit(
            _screenState.value.copy(
                description = text
            )
        )
    }

    private suspend fun onDatePickerButtonPressed(showOnlyTimePicker: Boolean) {
        val state = _screenState.value
        _screenState.emit(
            state.copy(
                isDatePickerVisible = !showOnlyTimePicker,
                isTimePickerVisible = showOnlyTimePicker && state.isTimeEnabled
            )
        )
    }

    private fun onDismissDatePickerClicked() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = true
            )
        )
    }

    private fun onDateOnlySelected(selectedDate: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = false,
                selectedDate = selectedDate
            )
        )
    }

    private fun onTimePickerDismissed(selectedDate: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = false,
                selectedDate = selectedDate
            )
        )
    }

    private fun onTimeEnabledChanged(isEnabled: Boolean, selectedDate: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                isTimeEnabled = isEnabled,
                isNotificationEnabled = if (isEnabled) _screenState.value.isNotificationEnabled else false,
                selectedDate = selectedDate,
                isTimePickerVisible = false
            )
        )
    }

    private fun onNotificationEnabledChanged(isEnabled: Boolean) {
        _screenState.tryEmit(
            _screenState.value.copy(
                isNotificationEnabled = isEnabled,
                isTimeEnabled = if (isEnabled) true else _screenState.value.isTimeEnabled
            )
        )
    }

    private fun onSchedulingFilterChipClicked(millis: Millis) {
        _screenState.tryEmit(
            _screenState.value.copy(
                selectedScheduleBeforeMillis = millis
            )
        )
    }
}
