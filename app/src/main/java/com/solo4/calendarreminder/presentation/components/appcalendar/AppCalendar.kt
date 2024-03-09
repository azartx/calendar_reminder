package com.solo4.calendarreminder.presentation.components.appcalendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarRow

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
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Notifier(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .alpha(if (rowItem.hasEvents) 1f else 0f)
                        )
                        AppCalendarBlock(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp)
                                ,
                            model = rowItem,
                            onItemClicked = onItemClick
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AppCalendarPreview() {
    AppCalendar(
        model = AppCalendarModel(
            List(4) {
                AppCalendarRow(
                    List(7) {
                        AppCalendarItemModel(
                            10, 10, 2024, 2
                        )
                    }
                )
            }
        ),

        ) {}
}