package com.solo4.core.mvi.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.component.KoinScopeComponent

interface ViewComponent<Nav : Any> : KoinScopeComponent {

    val componentContext: ComponentContext
    val navigation: StackNavigation<Nav>
}