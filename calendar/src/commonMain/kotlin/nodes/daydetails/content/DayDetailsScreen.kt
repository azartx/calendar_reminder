package com.solo4.calendarreminder.calendar.nodes.daydetails.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.state.DayDetailsScreenState
import com.solo4.core.calendar.model.CalendarEvent

@Composable
fun DayDetailsScreen(
    viewModel: DayDetailsViewModel,
    onEventDetailsClick: (event: CalendarEvent) -> Unit,
    onAddEventClick: (dayId: Long) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val currentDate = remember { viewModel.currentDate }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(vertical = 20.dp),
            text = currentDate,
            fontSize = 20.sp
        )

        if (screenState is DayDetailsScreenState.Loading) {
            CircularProgressIndicator()
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .weight(1f)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            (screenState as? DayDetailsScreenState.Content)?.let { content ->
                content.dayEvents.onEach { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 48.dp)
                            .clickable { onEventDetailsClick.invoke(event) }
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
            onClick = { onAddEventClick.invoke(viewModel.dayId) }
        ) {
            Text(text = "Add event")
        }
    }
}