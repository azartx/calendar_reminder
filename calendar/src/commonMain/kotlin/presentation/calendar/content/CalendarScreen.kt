package com.solo4.calendarreminder.calendar.presentation.calendar.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import appcalendar.AppCalendar
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnResume
import com.solo4.calendarreminder.shared.calendar.generated.resources.Res
import com.solo4.calendarreminder.shared.calendar.generated.resources.screen_calendar_button_add_event_label
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarScreen(
    modifier: Modifier,
    lifecycle: Lifecycle,
    viewModel: CalendarViewModel,
    onCalendarDayClicked: (Long) -> Unit,
    onAddEventClick: () -> Unit
) {
    val screenState by viewModel.calendarModel.collectAsState()

    LaunchedEffect("") {
        lifecycle.doOnResume {
            viewModel.loadScreenData()
        }
    }

    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
            textAlign = TextAlign.Center,
            text = screenState.modelFormattedDate
        )
        AppCalendar(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .fillMaxWidth(),
            model = screenState,
            onItemClick = { onCalendarDayClicked.invoke(it.dayId) },
            onHorizontalSwipe = { isRightSwipe ->
                viewModel.onCalendarSwiped(isRightSwipe)
            }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = onAddEventClick
        ) {
            Text(text = stringResource(Res.string.screen_calendar_button_add_event_label))
        }
    }
}