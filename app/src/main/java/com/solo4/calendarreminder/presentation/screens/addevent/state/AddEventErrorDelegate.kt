package com.solo4.calendarreminder.presentation.screens.addevent.state

import com.solo4.core.mvi.errorscreenstate.ErrorDelegate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddEventErrorDelegate : ErrorDelegate<AddEventScreenState, AddEventErrorState> {

    private val _errorState = MutableStateFlow(AddEventErrorState())
    override val errorState: StateFlow<AddEventErrorState> = _errorState.asStateFlow()

    override suspend fun isScreenStateValid(state: AddEventScreenState): Boolean {
        var isValid = true
        val currentState = _errorState.value
        val newState = currentState.copy(
            isTitleValid = state.title.isNotBlank().also { if (!it) isValid = false },
            isDescriptionValid = state.description.isNotBlank().also { if (!it) isValid = false }
        )
        return if (isValid) true else {
            _errorState.emit(newState)
            false
        }
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