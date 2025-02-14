package com.solo4.calendarreminder.calendar.nodes.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarScreen
import com.solo4.calendarreminder.calendar.nodes.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.mvi.decompose.ViewComponent
import org.koin.core.component.get

@OptIn(DelicateDecomposeApi::class)
class CalendarComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext {

    private val viewModel = componentContext.instanceKeeper.getOrCreate {
        get<CalendarViewModel>()
    }

    @Composable
    fun Content(modifier: Modifier) {
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