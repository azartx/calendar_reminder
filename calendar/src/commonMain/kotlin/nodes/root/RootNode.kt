package com.solo4.calendarreminder.calendar.nodes.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.pop
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.node.node
import com.solo4.calendarreminder.calendar.nodes.addevent.AddEventNode
import com.solo4.calendarreminder.calendar.nodes.daydetails.DayDetailsNode
import com.solo4.calendarreminder.calendar.nodes.eventdetails.EventDetailsNode
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.kmputils.MultiplatformContext
import com.solo4.domain.eventmanager.EventsNotificationManager
import com.solo4.domain.eventmanager.getEventsNotificationManager

class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.CalendarScreen,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackFader(it) }
    ),
    private val multiplatformContext: MultiplatformContext,
): Node<NavTarget>(
    appyxComponent = backStack,
    nodeContext = nodeContext
) {

    val eventsNotificationManager: EventsNotificationManager by lazy {
        getEventsNotificationManager(multiplatformContext, getPlatformCalendar())
    }

    @Composable
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(
            appyxComponent = backStack
        )
    }

    override fun buildChildNode(navTarget: NavTarget, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            //is NavTarget.CalendarScreen -> CalendarNode(nodeContext, backStack)
            is NavTarget.DayDetailsScreen ->
                DayDetailsNode(nodeContext, backStack, navTarget.dayId)
            is NavTarget.EventDetailsScreen ->
                EventDetailsNode(nodeContext, backStack, navTarget.event)
            is NavTarget.AddEventScreen ->
                AddEventNode(nodeContext, backStack, navTarget.concreteDay)
            else -> node(nodeContext, {})
        }
    }

    fun pop() {
        backStack.pop()
    }
}