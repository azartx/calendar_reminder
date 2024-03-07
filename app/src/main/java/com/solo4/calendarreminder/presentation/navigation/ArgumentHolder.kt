package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable

@Stable
object ArgumentHolder {

    private val args = mutableMapOf<Route, Map<String, Any>>()

    fun <T> getArg(route: Route, key: String): T {
        return synchronized(this) {
            args[route]!![key] as T
        }
    }

    fun setArg(route: Route, key: String, value: Any) {
        synchronized(this) {
            args.put(route, buildMap { put(key, value) })
        }
    }

    fun setArgs(route: Route, args: Map<String, Any>) {
        synchronized(this) {
            this.args.put(route, args)
        }
    }
}