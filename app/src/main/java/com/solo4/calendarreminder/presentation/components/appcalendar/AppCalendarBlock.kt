package com.solo4.calendarreminder.presentation.components.appcalendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel

@Composable
fun AppCalendarBlock(
    modifier: Modifier = Modifier,
    model: AppCalendarItemModel,
    onItemClicked: (AppCalendarItemModel) -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onItemClicked(model) }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            maxLines = 1,
            text = model.day.toString()
        )
    }
}