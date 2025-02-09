package com.solo4.calendarreminder.calendar.nodes.addevent

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.LeafNode
import com.solo4.calendarreminder.calendar.nodes.addevent.content.AddEventScreen
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget

class AddEventNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget>,
    private val concreteDay: Long?
) : LeafNode(nodeContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        AddEventScreen(
            concreteDay = concreteDay,
            onBackPressed = {
                backStack.pop()
            }
        )
    }
}