package com.solo4.calendarreminder.calendar.nodes.daydetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.DayDetailsScreen
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.DayDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.getPlatformCalendar

class DayDetailsNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget>,
    private val dayId: Long
) : LeafNode(nodeContext) {

    private val repository = CalendarRepository()

    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = viewModel<DayDetailsViewModel>(key = this.toString()) {
            DayDetailsViewModel(
                repository = repository,
                calendar = getPlatformCalendar(),
                dayId = dayId
            )
        }
        DayDetailsScreen(
            viewModel = viewModel,
            onEventDetailsClick = {
                backStack.push(NavTarget.EventDetailsScreen(it))
            },
            onAddEventClick = {
                backStack.push(NavTarget.AddEventScreen(it))
            }
        )
    }
}