package com.solo4.calendarreminder.calendar.nodes.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.push
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarScreen
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.nodes.calendar.content.factory.CalendarModelFactory
import com.solo4.calendarreminder.calendar.nodes.calendar.content.mapper.CalendarItemMapper
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.mvi.decompose.ViewComponent

@OptIn(DelicateDecomposeApi::class)
class CalendarComponent(
    override val navigation: StackNavigation<NavTarget>,
) : ViewComponent<NavTarget> {

    private val repository: CalendarRepository = CalendarRepository()
    private val calendar: CalendarWrapper = getPlatformCalendar()

    @Composable
    fun Content(modifier: Modifier) {
        val viewModel = viewModel<CalendarViewModel> {
            CalendarViewModel(
                calendarRepository = repository,
                calendarItemMapper = CalendarItemMapper(),
                calendarFactory = CalendarModelFactory(calendar),
                calendar = calendar
            )
        }
        CalendarScreen(
            modifier,
            viewModel = viewModel,
            onCalendarDayClicked = { dayId ->
                navigation.push(NavTarget.DayDetailsScreen(dayId))
            },
            onAddEventClick = {
                navigation.push(NavTarget.AddEventScreen(concreteDay = null))
            }
        )
    }
}