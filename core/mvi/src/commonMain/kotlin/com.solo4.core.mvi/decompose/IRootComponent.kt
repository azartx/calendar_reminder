package com.solo4.core.mvi.decompose

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface IRootComponent<Nav : Any> {

    val stack: Value<ChildStack<*, ViewComponent<Nav>>>
}