package com.solo4.core.mvi

import com.solo4.core.mvi.decompose.ViewComponent
import org.koin.core.component.createScope
import org.koin.core.scope.Scope

fun ViewComponent<*>.componentScope(): Lazy<Scope> {
    return lazy { createScope(source = this) }
}