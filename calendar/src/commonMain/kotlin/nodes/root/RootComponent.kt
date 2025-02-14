package com.solo4.calendarreminder.calendar.nodes.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.solo4.calendarreminder.calendar.nodes.addevent.AddEventComponent
import com.solo4.calendarreminder.calendar.nodes.calendar.CalendarComponent
import com.solo4.calendarreminder.calendar.nodes.daydetails.DayDetailsComponent
import com.solo4.calendarreminder.calendar.nodes.eventdetails.EventDetailsComponent
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.IRootComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext,
    IRootComponent<NavTarget> {

    private val navigation = StackNavigation<NavTarget>()

    override val stack: Value<ChildStack<*, ViewComponent<NavTarget>>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.CalendarScreen,
            handleBackButton = true,
            childFactory = ::childFactory,
        )

    init {
        backHandler.register(object : BackCallback() {
            override fun onBack() {
                isEnabled = stack.backStack.size != 1
                navigation.pop()
            }
        })
    }

    private fun childFactory(
        navTarget: NavTarget,
        componentContext: ComponentContext
    ): ViewComponent<NavTarget> {
        return when (navTarget) {
            is NavTarget.CalendarScreen -> CalendarComponent(navigation)
            is NavTarget.DayDetailsScreen -> DayDetailsComponent(navigation, navTarget.dayId)
            is NavTarget.EventDetailsScreen -> EventDetailsComponent(navigation, navTarget.event)
            is NavTarget.AddEventScreen -> AddEventComponent(navigation, navTarget.concreteDay)
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        MaterialTheme {
            Children(stack, modifier) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    when (val child = it.instance) {
                        is CalendarComponent -> child.Content(modifier)
                        is DayDetailsComponent -> child.Content(modifier)
                        is EventDetailsComponent -> child.Content(modifier)
                        is AddEventComponent -> child.Content(modifier)
                    }
                }
            }
        }
    }
}