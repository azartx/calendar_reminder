package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController

@Stable
val Route.name: String
    get() = this::class.java.name

@Stable
fun NavHostController.navigateWithArgs(route: Route, args: Map<String, Any> = emptyMap()) {
    ArgumentHolder.setArgs(route, args)
    navigate(route.name)
    /*args.forEach { (key, value) ->
        this.currentDestination?.addArgument(
            argumentName = key,
            argument = NavArgument.Builder()
                .setDefaultValue(value)
                .setIsNullable(false)
                .setType(NavType.inferFromValue(value.toString()))
                .build()
        )
    }*/
}

@Stable
fun NavHostController.navigateWithArg(route: Route, key: String, value: Any) {
    ArgumentHolder.setArg(route, key, value)
    navigate(route.name)
}