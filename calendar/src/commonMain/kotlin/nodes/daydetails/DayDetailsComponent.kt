package com.solo4.calendarreminder.calendar.nodes.daydetails

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DelicateDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.push
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.DayDetailsScreen
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.DayDetailsViewModel
import com.solo4.calendarreminder.calendar.nodes.root.NavTarget
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.mvi.decompose.ViewComponent
import org.koin.core.component.get

class DayDetailsComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
    private val dayId: Long
) : ViewComponent<NavTarget> {

    private val repository = CalendarRepository(get(), get())

    @OptIn(DelicateDecomposeApi::class)
    @Composable
    fun Content(modifier: Modifier) {
        val viewModel = viewModel<DayDetailsViewModel>(key = this.toString()) {
            DayDetailsViewModel(
                repository = repository,
                calendar = getPlatformCalendar(),
                dayId = dayId
            )
        }
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
}