package com.solo4.calendarreminder.shared.nodes.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import com.solo4.calendarreminder.shared.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.shared.nodes.calendar.content.CalendarScreen
import com.solo4.calendarreminder.shared.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.shared.nodes.calendar.content.factory.CalendarModelFactory
import com.solo4.calendarreminder.shared.nodes.calendar.content.mapper.CalendarItemMapper
import com.solo4.calendarreminder.shared.nodes.root.NavTarget
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getPlatformCalendar

class CalendarNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget>
) : LeafNode(nodeContext) {

    private val repository: CalendarRepository = CalendarRepository()
    private val calendar: CalendarWrapper = getPlatformCalendar()
    private val viewModel: CalendarViewModel = CalendarViewModel(
        calendarRepository = repository,
        calendarItemMapper = CalendarItemMapper(),
        calendarFactory = CalendarModelFactory(calendar),
        calendar = calendar
    )

    @Composable
    override fun Content(modifier: Modifier) {
        CalendarScreen(
            viewModel = viewModel,
            onCalendarDayClicked = { dayId ->

            },
            onAddEventClick = {

            }
        )
    }
}