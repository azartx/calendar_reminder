package com.solo4.core.mvi.decompose

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

open class ViewModel : InstanceKeeper.Instance {

    val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}