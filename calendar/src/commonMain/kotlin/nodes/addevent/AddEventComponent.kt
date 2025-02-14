package com.solo4.calendarreminder.calendar.nodes.addevent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.solo4.calendarreminder.calendar.nodes.addevent.content.AddEventScreen
import com.solo4.calendarreminder.calendar.nodes.addevent.content.AddEventViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.mvi.decompose.ViewComponent

class AddEventComponent(
    override val navigation: StackNavigation<NavTarget>,
    private val concreteDay: Long?
) : ViewComponent<NavTarget> {

    @Composable
    fun Content(modifier: Modifier) {
        val viewModel = viewModel<AddEventViewModel>(key = this.toString()) {
            AddEventViewModel(
                concreteDay = concreteDay
            )
        }
        AddEventScreen(
            modifier,
            viewModel = viewModel,
            onBackPressed = navigation::pop
        )
    }
}