package com.solo4.calendarreminder.shared.nodes.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.solo4.calendarreminder.shared.nodes.calendar.CalendarNode

class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.CalendarScreen,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackFader(it) }
    )
): Node<NavTarget>(
    appyxComponent = backStack,
    nodeContext = nodeContext
) {

    @Composable
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(
            appyxComponent = backStack
        )
    }

    override fun buildChildNode(navTarget: NavTarget, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            is NavTarget.CalendarScreen -> CalendarNode(nodeContext, backStack)
            is NavTarget.DayDetailsScreen -> node(nodeContext) {  }
            is NavTarget.EventDetailsScreen -> node(nodeContext) {  }
        }
    }

    fun pop() {
        backStack.pop()
    }
}