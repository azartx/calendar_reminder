package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
val Route.name: String
    get() = this::class.java.name

@Stable
inline fun <reified T : Route> NavHostController.navigateWithArgs(
    route: T,
    args: RouteArgs<T> = EmptyArgs as RouteArgs<T>
) {
    ArgumentHolder.setArgs(route, args)
    navigate(route.name)
}