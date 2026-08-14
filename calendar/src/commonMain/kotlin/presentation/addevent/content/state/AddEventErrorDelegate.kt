package com.solo4.calendarreminder.calendar.presentation.addevent.content.state

import com.solo4.core.mvi.errorscreenstate.ErrorDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEventErrorDelegate : ErrorDelegate<AddEventScreenState, AddEventErrorState> {

    private val _errorState = MutableStateFlow(AddEventErrorState())
    override val errorState: StateFlow<AddEventErrorState> = _errorState.asStateFlow()

    override suspend fun isScreenStateValid(state: AddEventScreenState): Boolean {
        val isTitleValid = state.title.isNotBlank()
        val isDescriptionValid = state.description.isNotBlank()
        val isValid = isTitleValid && isDescriptionValid
        _errorState.emit(
            AddEventErrorState(
                isTitleValid = isTitleValid,
                isDescriptionValid = isDescriptionValid
            )
        )
        return isValid
    }

    override suspend fun updateError(lambda: (AddEventErrorState) -> AddEventErrorState) {
        _errorState.emit(
            lambda.invoke(_errorState.value)
        )
    }

    override suspend fun clearErrors() {
        _errorState.emit(AddEventErrorState())
    }
}