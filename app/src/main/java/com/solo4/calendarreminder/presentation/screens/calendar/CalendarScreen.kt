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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.solo4.calendarreminder.presentation.components.appcalendar.AppCalendar
import com.solo4.calendarreminder.presentation.navigation.DayDetailsScreenArgs
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.navigation.navigateWithArgs
import kotlinx.coroutines.launch

@Composable
fun CalendarScreen(navController: NavHostController) {

    val viewModel: CalendarViewModel = viewModel()

    val screenState by viewModel.calendarModel.collectAsState()

    LifecycleResumeEffect(key1 = "") {
        val scope = lifecycleScope.launch { viewModel.onScreenResumed() }
        onPauseOrDispose { scope.cancel() }
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {

        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
            textAlign = TextAlign.Center,
            text = screenState.formattedCurrentDate
        )

        AppCalendar(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            model = screenState,
            onItemClick = {
                navController.navigateWithArgs(
                    Route.DayDetailsScreenRoute,
                    DayDetailsScreenArgs(it.dayId)
                )
            },
            onHorizontalSwipe = { isRightSwipe ->
                viewModel.onCalendarSwiped(isRightSwipe)
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navController.navigateWithArgs(Route.AddEventScreenRoute)
            }
        ) {
            Text(text = "Add new event")
        }
    }
}