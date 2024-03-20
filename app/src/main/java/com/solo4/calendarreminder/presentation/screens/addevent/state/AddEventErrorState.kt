package com.solo4.calendarreminder.presentation.screens.addevent.state

import com.solo4.core.mvi.errorscreenstate.ErrorState

data class AddEventErrorState(
    val isTitleValid: Boolean = true,
    val isDescriptionValid: Boolean = true
) : ErrorState