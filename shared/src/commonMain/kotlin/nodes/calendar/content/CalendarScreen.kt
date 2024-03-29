package com.solo4.calendarreminder.shared.nodes.calendar.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import appcalendar.AppCalendar
import com.bumble.appyx.navigation.lifecycle.LocalCommonLifecycleOwner
import kotlinx.coroutines.launch

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onCalendarDayClicked: (Long) -> Unit,
    onAddEventClick: () -> Unit
) {

    val lifecycle = LocalCommonLifecycleOwner.current
    val screenState by viewModel.calendarModel.collectAsState()

    DisposableEffect("") {
        val scope = lifecycle?.lifecycleScope?.launch { viewModel.onScreenResumed() }
        onDispose {
            scope?.cancel()
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
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
            Text(text = "Add new event")
        }
    }
}