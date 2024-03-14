package com.solo4.calendarreminder.presentation.screens.daydetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.solo4.calendarreminder.presentation.navigation.AddEventScreenArgs
import com.solo4.calendarreminder.presentation.navigation.EventDetailsScreenArgs
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.navigation.navigateWithArgs
import com.solo4.calendarreminder.presentation.screens.daydetails.state.DayDetailsScreenState
import kotlinx.coroutines.launch

@Composable
fun DayDetailsScreen(navController: NavHostController) {
    val viewModel = viewModel<DayDetailsViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    LifecycleResumeEffect(key1 = "") {
        val scope = lifecycleScope.launch { viewModel.onScreenResumed() }
        onPauseOrDispose { scope.cancel() }
    }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (screenState is DayDetailsScreenState.Loading) {
            CircularProgressIndicator()
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            (screenState as? DayDetailsScreenState.Content)?.let { content ->
                content.dayEvents.onEach { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigateWithArgs(
                                    Route.EventDetailsScreenRoute,
                                    EventDetailsScreenArgs(event)
                                )
                            }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = event.title
                        )
                    }
                }
                    .ifEmpty {
                        Text(
                            modifier = Modifier
                                .padding(top = 40.dp)
                                .fillMaxWidth(),
                            text = "No events on this day",
                            textAlign = TextAlign.Center
                        )
                    }
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigateWithArgs(
                    Route.AddEventScreenRoute,
                    AddEventScreenArgs(viewModel.dayId)
                )
            }
        ) {
            Text(text = "Add event")
        }
    }
}