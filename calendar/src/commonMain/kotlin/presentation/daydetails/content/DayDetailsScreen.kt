package com.solo4.calendarreminder.calendar.presentation.daydetails.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.state.DayDetailsScreenState
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.screen_calendar_button_add_event_label
import com.solo4.core.calendar.model.CalendarEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun DayDetailsScreen(
    modifier: Modifier,
    screenState: DayDetailsScreenState,
    currentDate: String,
    dayId: Long,
    onEventDetailsClick: (event: CalendarEvent) -> Unit,
    onAddEventClick: (dayId: Long) -> Unit
) {
    Column(
        modifier = modifier
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

        (screenState as? DayDetailsScreenState.Content)
            ?.dayEvents
            ?.takeIf { it.isNotEmpty() }
            ?.also { events ->
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(events) { event ->
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .fillMaxWidth()
                                .defaultMinSize(minHeight = 48.dp)
                                .border(BorderStroke(1.dp, Color.Black), RoundedCornerShape(16.dp))
                                .clickable { onEventDetailsClick.invoke(event) }
                        ) {
                            Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    text = event.title,
                                    fontSize = 20.sp
                                )
                                Text(
                                    modifier = Modifier,
                                    text = event.description
                                )
                            }
                        }
                    }
                }
            } ?: Text(
            modifier = Modifier
                .padding(top = 40.dp)
                .weight(1f)
                .fillMaxWidth(),
            text = "No events on this day",
            textAlign = TextAlign.Center
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { onAddEventClick.invoke(dayId) }
        ) {
            Text(text = stringResource(Res.string.screen_calendar_button_add_event_label))
        }
    }
}