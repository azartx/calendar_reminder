package appcalendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import appcalendar.model.AppCalendarItemModel
import appcalendar.model.AppCalendarModel
import com.solo4.calendarreminder.calendar.appcalendar.model.WeekDayNames
import com.solo4.calendarreminder.calendar.presentation.calendar.content.model.HorizontalSwipeDirection

@Composable
fun AppCalendar(
    model: AppCalendarModel,
    weekDayNames: WeekDayNames,
    modifier: Modifier,
    onHorizontalSwipe: (HorizontalSwipeDirection) -> Unit,
    onItemClick: (AppCalendarItemModel) -> Unit
) {

    var draggingPosition by remember { mutableFloatStateOf(0f) }
    Column(
        modifier = modifier.pointerInput("") {
            this.detectHorizontalDragGestures(
                onDragEnd = {
                    onHorizontalSwipe.invoke(
                        if (draggingPosition > 0) HorizontalSwipeDirection.RIGHT else HorizontalSwipeDirection.LEFT
                    )
                },
                onHorizontalDrag = { _, dragAmount ->
                    draggingPosition = dragAmount
                }
            )
        }
    ) {
        WeekDaysRow(
            modifier = Modifier.fillMaxWidth(),
            weekDayNames = weekDayNames
        )
        model.rows.forEach { rowData ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowData.rowItems.forEach { rowItem ->
                    Box(
                        modifier = Modifier.defaultMinSize(48.dp).weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        val isItemSelected = rowItem.year == model.yearNow &&
                            rowItem.month == model.monthNow &&
                            rowItem.day == model.dayNow
                        Canvas(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(48.dp)
                                .clickable(onClick = { onItemClick.invoke(rowItem) })
                        ) {
                            drawRoundRect(
                                color = if (isItemSelected)
                                    Color.Black.copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.1f),
                                cornerRadius = CornerRadius(30f, 30f)
                            )
                        }
                        if (rowItem.hasEvents) {
                            Notifier(modifier = Modifier.padding(top = 3.dp, end = 3.dp).align(Alignment.TopEnd))
                        }
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            maxLines = 1,
                            text = rowItem.day.toString()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeekDaysRow(modifier: Modifier, weekDayNames: WeekDayNames) {
    Row(modifier = modifier) {
        weekDayNames.asList.forEach { dayName ->
            Box(
                modifier = Modifier.defaultMinSize(48.dp).weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dayName,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}