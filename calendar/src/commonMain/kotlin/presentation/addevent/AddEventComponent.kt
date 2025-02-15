package com.solo4.calendarreminder.calendar.presentation.addevent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.solo4.calendarreminder.calendar.presentation.addevent.content.AddEventScreen
import com.solo4.calendarreminder.calendar.presentation.addevent.content.AddEventViewModel
import com.solo4.calendarreminder.calendar.presentation.addevent.content.model.AddEventDayParam
import com.solo4.calendarreminder.calendar.presentation.root.NavTarget
import com.solo4.core.mvi.componentScope
import com.solo4.core.mvi.decompose.DefaultLifecycleListener
import com.solo4.core.mvi.decompose.LifecycleListener
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class AddEventComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
    private val concreteDay: Long?
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext,
    LifecycleListener by DefaultLifecycleListener(componentContext.lifecycle) {

    override val scope: Scope by componentScope()

    private val viewModel = viewModel<AddEventViewModel, AddEventComponent> {
        parametersOf(AddEventDayParam(concreteDay))
    }

    @Composable
    fun Content(modifier: Modifier) {
        AddEventScreen(
            modifier,
            viewModel = viewModel,
            onBackPressed = navigation::pop
        )
    }

    override fun onDestroy() {
        closeScope()
    }
}