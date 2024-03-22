package com.solo4.core.kmputils

interface MultiplatformContext {
    fun getContext(): Any?

    fun setContext(context: Any?)

    fun dispose()
}