package com.solo4.core.mvi.screenstate

import com.solo4.core.mvi.errorscreenstate.ErrorDelegate
import com.solo4.core.mvi.errorscreenstate.ErrorState
import com.solo4.core.mvi.screenevent.ScreenEvent
import kotlinx.coroutines.flow.StateFlow

interface ScreenStateDelegate<S : ScreenState, E: ErrorState, SE : ScreenEvent<S>> {

    val screenState: StateFlow<S>
    val errorDelegate: ErrorDelegate<S, E>

    suspend fun handleEvent(event: SE)
}