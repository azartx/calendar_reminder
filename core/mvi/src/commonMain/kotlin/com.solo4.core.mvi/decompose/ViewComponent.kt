package com.solo4.core.mvi.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import org.koin.core.component.KoinComponent

interface ViewComponent<Nav : Any> : KoinComponent {

    val componentContext: ComponentContext
    val navigation: StackNavigation<Nav>
}