package com.solo4.calendarreminder.calendar.nodes.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.solo4.calendarreminder.calendar.nodes.calendar.CalendarNode
import com.solo4.core.mvi.decompose.ChildComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<NavTarget>()

    val stack: Value<ChildStack<*, ChildComponent>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.CalendarScreen,
            handleBackButton = true, // Automatically pop from the stack on back button presses
            childFactory = ::child,
        )

    private fun child(
        navTarget: NavTarget,
        componentContext: ComponentContext
    ): ChildComponent {
        return CalendarNode(this)
    }
        /*when (navTarget) {
            is NavTarget.CalendarScreen -> CalendarNode(nodeContext, backStack)
            is NavTarget.DayDetailsScreen ->
                DayDetailsNode(nodeContext, backStack, navTarget.dayId)

            is NavTarget.EventDetailsScreen ->
                EventDetailsNode(nodeContext, backStack, navTarget.event)

            is NavTarget.AddEventScreen ->
                AddEventNode(nodeContext, backStack, navTarget.concreteDay)
        }*/

}