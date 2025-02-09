package com.solo4.core.mvi.errorscreenstate

import com.solo4.core.mvi.screenstate.ScreenState
import kotlinx.coroutines.flow.StateFlow

interface ErrorDelegate<S: ScreenState, E : ErrorState> {

    val errorState: StateFlow<E>

    suspend fun isScreenStateValid(state: S): Boolean

    suspend fun updateError(lambda: (E) -> E)

    suspend fun clearErrors()
}