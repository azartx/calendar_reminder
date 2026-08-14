package com.solo4.calendarreminder.calendar.presentation.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.solo4.calendarreminder.calendar.presentation.addevent.AddEventComponent
import com.solo4.calendarreminder.calendar.presentation.calendar.CalendarComponent
import com.solo4.calendarreminder.calendar.presentation.daydetails.DayDetailsComponent
import com.solo4.calendarreminder.calendar.presentation.eventdetails.EventDetailsComponent
import com.solo4.calendarreminder.calendar.presentation.settings.SettingsComponent
import com.solo4.core.mvi.decompose.ViewComponent
import com.solo4.core.mvi.decompose.IRootComponent
import org.koin.core.component.KoinComponent

class RootComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext,
    IRootComponent<NavTarget>,
    KoinComponent {

    private val navigation = StackNavigation<NavTarget>()

    override val stack: Value<ChildStack<*, ViewComponent<NavTarget>>> =
        childStack(
            source = navigation,
            serializer = NavTarget.serializer(),
            initialConfiguration = NavTarget.CalendarScreen,
            handleBackButton = true,
            childFactory = ::childFactory,
        )

    private fun childFactory(
        navTarget: NavTarget,
        componentContext: ComponentContext
    ): ViewComponent<NavTarget> {
        return when (navTarget) {
            is NavTarget.CalendarScreen -> CalendarComponent(componentContext, navigation)
            is NavTarget.DayDetailsScreen -> DayDetailsComponent(componentContext, navigation, navTarget.dayId)
            is NavTarget.EventDetailsScreen -> EventDetailsComponent(componentContext, navigation, navTarget.event)
            is NavTarget.AddEventScreen -> AddEventComponent(componentContext, navigation, navTarget.concreteDay)
            is NavTarget.SettingsScreen -> SettingsComponent(componentContext, navigation)
        }
    }

    @Composable
    fun Content(modifier: Modifier) {
        MaterialTheme {
            Children(stack, modifier.fillMaxSize().statusBarsPadding()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (val child = it.instance) {
                        is CalendarComponent -> child.Content(Modifier.fillMaxSize())
                        is DayDetailsComponent -> child.Content(Modifier.fillMaxSize())
                        is EventDetailsComponent -> child.Content(Modifier.fillMaxSize())
                        is AddEventComponent -> child.Content(Modifier.fillMaxSize())
                        is SettingsComponent -> child.Content(Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}