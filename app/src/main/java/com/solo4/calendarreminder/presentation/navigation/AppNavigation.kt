package com.solo4.calendarreminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.solo4.calendarreminder.presentation.screens.addevent.AddEventScreen
import com.solo4.calendarreminder.presentation.screens.calendar.CalendarScreen
import com.solo4.calendarreminder.presentation.screens.daydetails.DayDetailsScreen
import com.solo4.calendarreminder.presentation.screens.eventdetails.EventDetailsScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Route.CalendarScreenRoute::class.java.name) {
        composable(Route.CalendarScreenRoute::class.java.name) {
            CalendarScreen(navController)
        }
        composable(Route.AddEventScreenRoute::class.java.name) {
            AddEventScreen(navController)
        }
        composable(Route.DayDetailsScreenRoute::class.java.name) {
            DayDetailsScreen(navController)
        }
        composable(Route.EventDetailsScreenRoute::class.java.name) {
            EventDetailsScreen()
        }
    }
}