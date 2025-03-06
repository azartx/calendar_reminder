package com.solo4.calendarreminder.calendar.presentation.calendar.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import appcalendar.AppCalendar
import appcalendar.model.AppCalendarModel
import com.solo4.calendarreminder.calendar.appcalendar.model.WeekDayNames
import com.solo4.calendarreminder.calendar.presentation.calendar.content.model.HorizontalSwipeDirection
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.screen_calendar_button_add_event_label
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_friday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_monday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_saturday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_sunday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_thursday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_tuesday
import com.solo4.calendarreminder.shared.calendar.generated.resources.week_day_wednesday
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarScreen(
    modifier: Modifier,
    calendarState: AppCalendarModel,
    onCalendarDayClicked: (Long) -> Unit,
    onAddEventClick: () -> Unit,
    onCalendarHorizontalSwipe: (HorizontalSwipeDirection) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                textAlign = TextAlign.Center,
                text = calendarState.modelFormattedDate
            )
            AppCalendar(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                model = calendarState,
                weekDayNames = rememberWeekDays(),
                onItemClick = { onCalendarDayClicked.invoke(it.dayId) },
                onHorizontalSwipe = onCalendarHorizontalSwipe,
            )
        }

        Button(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = onAddEventClick
        ) {
            Text(text = stringResource(Res.string.screen_calendar_button_add_event_label))
        }
    }
}

@Composable
fun rememberWeekDays(): WeekDayNames {
    val monday = stringResource(Res.string.week_day_monday)
    val tuesday = stringResource(Res.string.week_day_tuesday)
    val wednesday = stringResource(Res.string.week_day_wednesday)
    val thursday = stringResource(Res.string.week_day_thursday)
    val friday = stringResource(Res.string.week_day_friday)
    val saturday = stringResource(Res.string.week_day_saturday)
    val sunday = stringResource(Res.string.week_day_sunday)

    return remember(monday) {
        WeekDayNames(monday, tuesday, wednesday, thursday, friday, saturday, sunday)
    }
}