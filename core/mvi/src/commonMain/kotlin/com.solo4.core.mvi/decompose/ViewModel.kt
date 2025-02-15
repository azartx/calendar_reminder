package com.solo4.core.mvi.decompose

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition

open class ViewModel : InstanceKeeper.Instance {

    val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}

inline fun <reified VM : ViewModel, VC> VC.viewModel(
    noinline parameters: ParametersDefinition? = null
): VM where VC : ViewComponent<*>, VC : KoinScopeComponent {
    return componentContext.instanceKeeper.getOrCreate { get<VM>(parameters = parameters) }
}