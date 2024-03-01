package com.solo4.calendarreminder.presentation.screens.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun CalendarScreen(navController: NavHostController) {

    val viewModel: CalendarViewModel = viewModel()

    val screenState by viewModel.calendarModel.collectAsState()

    Column {
        AppCalendar(
            modifier = Modifier.fillMaxWidth(),
            model = screenState
        ) { clickedDay ->

        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { navController.navigate(Route.AddEventScreenRoute::class.java.name) }
        ) {
            Text(text = "Add new event")
        }
    }
}