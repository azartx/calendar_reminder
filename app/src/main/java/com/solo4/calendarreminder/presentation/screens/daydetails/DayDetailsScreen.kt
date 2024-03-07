package com.solo4.calendarreminder.presentation.screens.daydetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.solo4.calendarreminder.presentation.navigation.Route
import com.solo4.calendarreminder.presentation.navigation.Route.ArgKeys.EVENT_DETAILS
import com.solo4.calendarreminder.presentation.navigation.navigateWithArg
import com.solo4.calendarreminder.presentation.screens.daydetails.state.DayDetailsScreenState

@Composable
fun DayDetailsScreen(navController: NavHostController) {
    val viewModel = viewModel<DayDetailsViewModel>()
    val screenState by viewModel.screenState.collectAsState()

    if (screenState is DayDetailsScreenState.Loading) {
        CircularProgressIndicator()
    }

    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        (screenState as? DayDetailsScreenState.Content)?.let { content ->
            content.dayEvents.forEach { event ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigateWithArg(Route.EventDetailsScreenRoute, EVENT_DETAILS, event) }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = event.title
                    )
                }
            }
        } ?: Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
            text = "You have no events here"
        )
    }
}