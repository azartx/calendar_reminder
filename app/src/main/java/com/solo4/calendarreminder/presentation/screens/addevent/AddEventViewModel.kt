package com.solo4.calendarreminder.presentation.screens.addevent

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.presentation.screens.addevent.state.AddEventScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class AddEventViewModel : ViewModel() {

    private val _datePickerState = MutableStateFlow(DatePickerState(Locale.getDefault()))
    val datePickerState = _datePickerState.asStateFlow()

    private val _screenState = MutableStateFlow(AddEventScreenState())
    val screenState = _screenState.asStateFlow()

    fun onTitleTextFieldChanged(value: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                title = value
            )
        )
    }

    fun onDescriptionTextFieldChanged(value: String) {
        _screenState.tryEmit(
            _screenState.value.copy(
                description = value
            )
        )
    }

    fun onDatePickerButtonPressed() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = true
            )
        )
    }

    fun onDismissDatePickerClicked() {
        _screenState.tryEmit(
            _screenState.value.copy(
                isDatePickerVisible = false,
                selectedDate = getFormatTimeWithTZ(Date(datePickerState.value.selectedDateMillis ?: 0))
            )
        )
    }

    fun onSubmitButtonClicked() {
        // todo save event logic
    }

    private fun getFormatTimeWithTZ(currentTime: Date): String {
        val timeZoneDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        return timeZoneDate.format(currentTime);
    }
}