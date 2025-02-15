package com.solo4.calendarreminder.calendar.nodes.eventdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsScreen
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.mvi.componentScope
import com.solo4.core.mvi.decompose.DefaultLifecycleListener
import com.solo4.core.mvi.decompose.LifecycleListener
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class EventDetailsComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
    private val targetEvent: CalendarEvent
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext,
    LifecycleListener by DefaultLifecycleListener(componentContext.lifecycle) {

    override val scope: Scope by componentScope()

    private val viewModel = viewModel<EventDetailsViewModel, EventDetailsComponent> {
        parametersOf(targetEvent)
    }

    @Composable
    fun Content(modifier: Modifier) {
        EventDetailsScreen(modifier, viewModel)
    }

    override fun onDestroy() {
        closeScope()
    }
}