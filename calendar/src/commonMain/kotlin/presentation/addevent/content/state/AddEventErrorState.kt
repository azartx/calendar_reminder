package com.solo4.calendarreminder.calendar.presentation.addevent.content.state

import com.solo4.core.mvi.errorscreenstate.ErrorState

data class AddEventErrorState(
    val isTitleValid: Boolean = true,
    val isDescriptionValid: Boolean = true
) : ErrorState