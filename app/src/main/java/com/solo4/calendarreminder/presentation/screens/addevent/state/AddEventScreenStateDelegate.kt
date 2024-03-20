package com.solo4.calendarreminder.presentation.screens.addevent.state

import com.solo4.core.mvi.errorscreenstate.ErrorDelegate
import com.solo4.core.mvi.screenstate.ScreenStateDelegate
import com.solo4.calendarreminder.data.utils.Millis
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
        when(event) {
            is AddEventScreenEvent.OnTitleTextChanged -> onTitleChanged(event.text)
            is AddEventScreenEvent.OnDescriptionTextChanged -> onDescriptionChanged(event.text)
            is AddEventScreenEvent.OnDatePickerButtonPressed -> onDatePickerButtonPressed(event.showOnlyTimePicker)
            is AddEventScreenEvent.OnDismissDatePickerClicked -> onDismissDatePickerClicked()
            is AddEventScreenEvent.OnTimePickerDismissed -> onTimePickerDismissed(event.selectedDate)
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
        _screenState.emit(
            _screenState.value.copy(
                // Показываем дэйт пикер если нет предустановленного дня
                isDatePickerVisible = !showOnlyTimePicker,
                // Показываем тайм пикер, если ден был заранее установлен
                isTimePickerVisible = showOnlyTimePicker
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

    private fun onTimePickerDismissed(selectedDate: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                isTimePickerVisible = false,
                selectedDate = selectedDate
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