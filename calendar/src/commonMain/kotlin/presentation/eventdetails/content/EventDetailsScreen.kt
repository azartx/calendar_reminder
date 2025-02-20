package com.solo4.calendarreminder.calendar.presentation.eventdetails.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.ic_delete
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.uicomponents.Toolbar
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventDetailsScreen(
    modifier: Modifier,
    event: CalendarEvent,
    onBackPressed: () -> Unit,
    onRemoveEventClick: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Toolbar(
            title = event.title,
            onBackPressed = onBackPressed,
            actions = {
                IconButton(onClick = onRemoveEventClick) { // TODO add dialog for confirmation
                    Icon(
                        painterResource(Res.drawable.ic_delete),
                        contentDescription = "Remove event"
                    )
                }

            }
        )
        Text(
            modifier = Modifier.padding(all = 20.dp).fillMaxWidth(),
            text = event.eventTimeMillis.toDateByPattern(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Right
        )
        Text(
            modifier = Modifier.padding(horizontal = 20.dp).weight(1f),
            text = event.description,
        )
    }
}