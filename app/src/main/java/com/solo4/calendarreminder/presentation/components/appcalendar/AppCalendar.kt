package com.solo4.calendarreminder.presentation.components.appcalendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarModel

@Composable
fun AppCalendar(
    model: AppCalendarModel,
    modifier: Modifier = Modifier,
    onItemClick: (AppCalendarItemModel) -> Unit
) {
    Column(modifier = modifier) {
        model.rows.forEach { rowData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                rowData.rowItems.forEach { rowItem ->
                    AppCalendarBlock(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 48.dp)
                            .weight(1f),
                        model = rowItem,
                        onItemClicked = onItemClick
                    )
                }
            }
        }
    }
}