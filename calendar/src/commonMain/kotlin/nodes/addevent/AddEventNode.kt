package com.solo4.calendarreminder.calendar.nodes.addevent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import com.solo4.calendarreminder.calendar.nodes.addevent.content.AddEventScreen
import com.solo4.calendarreminder.calendar.nodes.addevent.content.AddEventViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget

class AddEventNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget>,
    private val concreteDay: Long?
) : LeafNode(nodeContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        val viewModel = viewModel<AddEventViewModel>(key = this.toString()) {
            AddEventViewModel(
                concreteDay = concreteDay
            )
        }
        AddEventScreen(
            viewModel = viewModel,
            onBackPressed = {
                backStack.pop()
            }
        )
    }
}