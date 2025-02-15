package com.solo4.core.mvi.decompose

import com.arkivanov.essenty.lifecycle.Lifecycle

class DefaultLifecycleListener(
    private val lifecycle: Lifecycle
) : LifecycleListener {

    private val callbacks = object : Lifecycle.Callbacks {
        override fun onCreate() {
            this@DefaultLifecycleListener.onCreate()
        }

        override fun onDestroy() {
            this@DefaultLifecycleListener.onDestroy()
            lifecycle.unsubscribe(callbacks = this)
        }

        override fun onPause() {
            this@DefaultLifecycleListener.onPause()
        }

        override fun onResume() {
            this@DefaultLifecycleListener.onResume()
        }

        override fun onStart() {
            this@DefaultLifecycleListener.onStart()
        }

        override fun onStop() {
            this@DefaultLifecycleListener.onStop()
        }
    }

    init {
        lifecycle.subscribe(callbacks = callbacks)
    }
}