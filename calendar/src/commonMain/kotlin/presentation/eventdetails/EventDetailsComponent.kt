package com.solo4.calendarreminder.calendar.presentation.eventdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.solo4.calendarreminder.calendar.presentation.eventdetails.content.EventDetailsScreen
import com.solo4.calendarreminder.calendar.presentation.eventdetails.content.EventDetailsViewModel
import com.solo4.calendarreminder.calendar.presentation.root.NavTarget
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
    }.apply {
        registerOnBackEvent {
            navigation.pop()
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        val event by viewModel.calendarEventState.collectAsState()
        EventDetailsScreen(
            modifier,
            event,
            onEventUpdated = viewModel::updateEvent,
            onEventChangesSaved = viewModel::saveEventChanges,
            navigation::pop,
            viewModel::removeEvent
        )
    }

    override fun onDestroy() {
        closeScope()
    }
}