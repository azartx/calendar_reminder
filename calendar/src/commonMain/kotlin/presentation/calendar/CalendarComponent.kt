package com.solo4.calendarreminder.calendar.presentation.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.essenty.lifecycle.doOnResume
import com.solo4.calendarreminder.calendar.presentation.calendar.content.CalendarScreen
import com.solo4.calendarreminder.calendar.presentation.calendar.content.CalendarViewModel
import com.solo4.calendarreminder.calendar.presentation.root.NavTarget
import com.solo4.core.mvi.componentScope
import com.solo4.core.mvi.decompose.DefaultLifecycleListener
import com.solo4.core.mvi.decompose.LifecycleListener
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.viewModel
import org.koin.core.scope.Scope

@OptIn(DelicateDecomposeApi::class)
class CalendarComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext,
    LifecycleListener by DefaultLifecycleListener(componentContext.lifecycle) {

    override val scope: Scope by componentScope()

    private val viewModel = viewModel<CalendarViewModel, CalendarComponent>()

    @Composable
    fun Content(modifier: Modifier) {
        LaunchedEffect("") {
            lifecycle.doOnResume {
                viewModel.loadScreenData()
            }
        }
        val screenState by viewModel.calendarModel.collectAsState()
        CalendarScreen(
            modifier,
            calendarState = screenState,
            onCalendarDayClicked = { dayId ->
                navigation.push(NavTarget.DayDetailsScreen(dayId))
            },
            onAddEventClick = {
                navigation.push(NavTarget.AddEventScreen(concreteDay = null))
            },
            onCalendarHorizontalSwipe = viewModel::onCalendarSwiped
        )
    }

    override fun onDestroy() {
        closeScope()
    }
}