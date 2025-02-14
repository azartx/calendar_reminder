package com.solo4.calendarreminder.calendar.nodes.eventdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsScreen
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.mvi.decompose.ViewComponent

class EventDetailsComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
    private val targetEvent: CalendarEvent
) : ViewComponent<NavTarget> {

    @Composable
    fun Content(modifier: Modifier) {
        val viewModel = viewModel(key = this.toString()) {
            EventDetailsViewModel(targetEvent)
        }
        EventDetailsScreen(modifier, viewModel)
    }
}