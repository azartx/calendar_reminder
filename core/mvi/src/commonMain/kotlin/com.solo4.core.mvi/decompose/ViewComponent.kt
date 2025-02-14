package com.solo4.core.mvi.decompose

import com.arkivanov.decompose.router.stack.StackNavigation

interface ViewComponent<Nav : Any> {

    val navigation: StackNavigation<Nav>
}