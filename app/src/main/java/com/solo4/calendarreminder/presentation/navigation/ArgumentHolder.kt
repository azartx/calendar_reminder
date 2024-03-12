package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable

@Stable
object ArgumentHolder {

    private val args = mutableMapOf<Route, RouteArgs<out Route>>()

    fun <R : Route, RA : RouteArgs<R>> getArg(route: R): RA {
        return synchronized(this) {
            val args = args[route]!!
            if (args is EmptyArgs) throw EmptyArgsException()
            args as RA
        }
    }

    fun <R : Route, RA : RouteArgs<R>> getArgOrNull(route: R): RA? {
        return synchronized(this) {
            val args = args[route]
            if (args is EmptyArgs) null else args as? RA
        }
    }

    fun <R : Route, RA : RouteArgs<R>> setArgs(route: R, args: RA? = null) {
        synchronized(this) {
            this.args.put(route, args ?: EmptyArgs)
        }
    }

    class EmptyArgsException : Exception()
}