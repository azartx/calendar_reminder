package com.solo4.calendarreminder.calendar.nodes.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarScreen
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.nodes.calendar.content.factory.CalendarModelFactory
import com.solo4.calendarreminder.calendar.nodes.calendar.content.mapper.CalendarItemMapper
import com.solo4.calendarreminder.calendar.nodes.root.RootComponent
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.mvi.decompose.ChildComponent

class CalendarNode(
    private val rootComponent: RootComponent,
) : ChildComponent {

    private val repository: CalendarRepository = CalendarRepository()
    private val calendar: CalendarWrapper = getPlatformCalendar()

    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = viewModel<CalendarViewModel> {
            CalendarViewModel(
                calendarRepository = repository,
                calendarItemMapper = CalendarItemMapper(),
                calendarFactory = CalendarModelFactory(calendar),
                calendar = calendar
            )
        }
        CalendarScreen(
            viewModel = viewModel,
            onCalendarDayClicked = { dayId ->
               // backStack.push(NavTarget.DayDetailsScreen(dayId))
            },
            onAddEventClick = {
                //backStack.push(NavTarget.AddEventScreen(concreteDay = null))
            }
        )
    }
}