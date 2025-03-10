package com.solo4.calendarreminder.calendar.presentation.eventdetails.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.ic_delete
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.uicomponents.Dialog
import com.solo4.core.uicomponents.Toolbar
import org.jetbrains.compose.resources.painterResource

@Composable
fun EventDetailsScreen(
    modifier: Modifier,
    event: CalendarEvent,
    onEventUpdated: (CalendarEvent) -> Unit,
    onEventChangesSaved: (CalendarEvent) -> Unit,
    onBackPressed: () -> Unit,
    onRemoveEventClick: () -> Unit,
) {
    var isDialogVisible by remember { mutableStateOf(value = false) }
    var isInEditMode by remember { mutableStateOf(value = false) }

    Dialog(
        title = "Remove the event",
        description = "Are you sure you want to remove the event \"${event.title}\"?",
        isVisible = isDialogVisible,
        onDismissClicked = { isDialogVisible = false },
        onConfirmClicked = {
            onRemoveEventClick.invoke()
            isDialogVisible = false
        }
    )
    Column(modifier = modifier.fillMaxSize()) {
        Toolbar(
            title = event.title,
            onBackPressed = onBackPressed,
            actions = {
                IconButton(onClick = { isDialogVisible = true }) {
                    Icon(
                        painterResource(Res.drawable.ic_delete),
                        contentDescription = "Remove event"
                    )
                }
                IconButton(
                    onClick = {
                        val newValue = !isInEditMode
                        isInEditMode = newValue
                        if (!newValue) {
                            onEventChangesSaved.invoke(event)
                        }
                    }
                ) {
                    Icon(
                        if (isInEditMode) Icons.Default.Done else Icons.Default.Edit,
                        contentDescription = if (isInEditMode) "Edit event" else "Apply changes"
                    )
                }
            }
        )
        // if it is in edit mode - show text fields for editing, otherwise show text for viewing
        Text(
            modifier = Modifier.padding(all = 20.dp).fillMaxWidth(),
            text = event.eventTimeMillis.toDateByPattern(),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Right
        )
        if (isInEditMode) {
            // TODO add time changing using dialog
            // TODO create own date time dialog
            /*TextField(
                modifier = Modifier.padding(all = 20.dp).fillMaxWidth(),
                enabled = isInEditMode,
                value = event.eventTimeMillis.toDateByPattern(),
                onValueChange = { onEventUpdated.invoke(event.copy(title = it)) },
                textStyle = MaterialTheme.typography.bodyMedium,
            )*/
            TextField(
                modifier = Modifier.padding(horizontal = 20.dp).weight(1f),
                enabled = isInEditMode,
                value = event.description,
                onValueChange = { onEventUpdated.invoke(event.copy(description = it)) },
            )
        } else {
            Text(
                modifier = Modifier.padding(horizontal = 20.dp).weight(1f),
                text = event.description,
            )
        }
    }
}