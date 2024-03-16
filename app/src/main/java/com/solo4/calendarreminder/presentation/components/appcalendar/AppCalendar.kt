package com.solo4.calendarreminder.presentation.components.appcalendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarItemModel
import com.solo4.calendarreminder.presentation.components.appcalendar.model.AppCalendarModel

@Composable
fun AppCalendar(
    model: AppCalendarModel,
    modifier: Modifier = Modifier,
    onHorizontalSwipe: (Boolean) -> Unit,
    onItemClick: (AppCalendarItemModel) -> Unit
) {
    var draggingPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier.pointerInput("") {
            this.detectHorizontalDragGestures(
                onDragEnd = {
                    onHorizontalSwipe.invoke(draggingPosition > 0)
                },
                onHorizontalDrag = { _, dragAmount ->
                    draggingPosition = dragAmount
                }
            )
        }
    ) {
        model.rows.forEach { rowData ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            ) {
                rowData.rowItems.forEach { rowItem ->
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        if (
                            rowItem.year == model.yearNow &&
                            rowItem.month == model.monthNow &&
                            rowItem.day == model.dayNow
                        ) {
                            Canvas(modifier = Modifier.size(48.dp)) {
                                drawRoundRect(
                                    color = Color.Blue,
                                    cornerRadius = CornerRadius(100f, 100f)
                                )
                            }
                        }
                        Notifier(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .alpha(if (rowItem.hasEvents) 1f else 0f)
                        )
                        AppCalendarBlock(
                            modifier = Modifier
                                .defaultMinSize(minHeight = 48.dp),
                            model = rowItem,
                            onItemClicked = onItemClick
                        )
                    }
                }
            }
        }
    }
}