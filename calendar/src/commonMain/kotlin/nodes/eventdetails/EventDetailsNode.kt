package com.solo4.calendarreminder.calendar.nodes.eventdetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsScreen
import com.solo4.calendarreminder.calendar.nodes.eventdetails.content.EventDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.model.CalendarEvent

class EventDetailsNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget>,
    private val targetEvent: CalendarEvent
) : LeafNode(nodeContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = viewModel(key = this.toString()) {
            EventDetailsViewModel(targetEvent)
        }
        EventDetailsScreen(viewModel)
    }
}