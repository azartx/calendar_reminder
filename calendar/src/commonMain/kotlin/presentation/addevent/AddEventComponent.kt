package com.solo4.calendarreminder.calendar.presentation.addevent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Content(modifier: Modifier) {
        val screenState by viewModel.screenState.collectAsState()
        val errorState by viewModel.errorDelegate.errorState.collectAsState()
        val datePickerState by viewModel.datePickerState.collectAsState()
        val timePickerState by viewModel.timePickerState.collectAsState()
        val coroutineScope = rememberCoroutineScope()
        DisposableEffect(key1 = "") {
            val job = coroutineScope.launch {
                viewModel.navigationState.collectLatest { navigation.pop() }
            }

            onDispose { job.cancel() }
        }
        AddEventScreen(
            modifier,
            screenState = screenState,
            errorState = errorState,
            datePickerState = datePickerState,
            timePickerState = timePickerState,
            scheduleBeforeMillis = remember { viewModel.scheduleBeforeMillis },
            onDismissDatePickerClick = viewModel::onDismissDatePickerClicked,
            onDismissTimePickerClick = viewModel::onTimePickerDismissed,
            onSchedulingFilterChipClicked = viewModel::onSchedulingFilterChipClicked,
            onTitleTextFieldChanged = viewModel::onTitleTextFieldChanged,
            onDescriptionTextFieldChanged = viewModel::onDescriptionTextFieldChanged,
            onDatePickerButtonPressed = viewModel::onDatePickerButtonPressed,
            onSubmitButtonClicked = viewModel::onSubmitButtonClicked,
            onBackPressed = navigation::pop
        )
    }

    override fun onDestroy() {
        closeScope()
    }
}