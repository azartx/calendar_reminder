package com.solo4.calendarreminder.presentation.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.solo4.calendarreminder.presentation.components.appcalendar.AppCalendar
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.navigation.Route.ArgKeys.DAY_ID
import com.solo4.calendarreminder.presentation.navigation.name
import com.solo4.calendarreminder.presentation.navigation.navigateWithArg
import com.solo4.calendarreminder.presentation.navigation.navigateWithArgs

@Composable
fun CalendarScreen(navController: NavHostController) {

    val viewModel: CalendarViewModel = viewModel()

    val screenState by viewModel.calendarModel.collectAsState()

    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        AppCalendar(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            model = screenState,
            onItemClick = {
                navController.navigateWithArg(Route.DayDetailsScreenRoute, DAY_ID, it.dayId)
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { navController.navigate(Route.AddEventScreenRoute.name) }
        ) {
            Text(text = "Add new event")
        }
    }
}