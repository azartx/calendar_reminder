package com.solo4.calendarreminder.calendar.nodes.daydetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
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
    dayId: Long
) : LeafNode(nodeContext) {

    private val repository = CalendarRepository()
    private val viewModel = DayDetailsViewModel(
        repository = repository,
        calendar = getPlatformCalendar(),
        dayId = dayId
    )

    @Composable
    override fun Content(modifier: Modifier) {
        DayDetailsScreen(
            viewModel = viewModel
        )
    }
}