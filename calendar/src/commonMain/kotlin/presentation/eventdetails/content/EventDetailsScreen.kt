package com.solo4.calendarreminder.calendar.presentation.eventdetails.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.toDateByPattern
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.uicomponents.Toolbar

@Composable
fun EventDetailsScreen(
    modifier: Modifier,
    event: CalendarEvent,
    onBackPressed: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Toolbar(
            title = event.title,
            onBackPressed = onBackPressed
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