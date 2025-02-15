package com.solo4.calendarreminder.calendar.presentation.daydetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.DayDetailsScreen
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.DayDetailsViewModel
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.model.DayIdParam
import com.solo4.calendarreminder.calendar.presentation.root.NavTarget
import com.solo4.core.mvi.componentScope
import com.solo4.core.mvi.decompose.DefaultLifecycleListener
import com.solo4.core.mvi.decompose.LifecycleListener
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class DayDetailsComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
    private val dayId: Long
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext,
    LifecycleListener by DefaultLifecycleListener(componentContext.lifecycle) {

    override val scope: Scope by componentScope()

    private val viewModel = viewModel<DayDetailsViewModel, DayDetailsComponent> {
        parametersOf(DayIdParam(dayId))
    }

    @OptIn(DelicateDecomposeApi::class)
    @Composable
    fun Content(modifier: Modifier) {
        DayDetailsScreen(
            modifier,
            viewModel = viewModel,
            onEventDetailsClick = {
                navigation.push(NavTarget.EventDetailsScreen(it))

            },
            onAddEventClick = {
                navigation.push(NavTarget.AddEventScreen(it))
            }
        )
    }

    override fun onDestroy() {
        closeScope()
    }
}