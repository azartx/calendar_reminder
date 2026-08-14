package com.solo4.calendarreminder.calendar.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.solo4.calendarreminder.calendar.presentation.root.NavTarget
import com.solo4.calendarreminder.calendar.presentation.settings.content.SettingsScreen
import com.solo4.core.mvi.componentScope
import com.solo4.core.mvi.decompose.DefaultLifecycleListener
import com.solo4.core.mvi.decompose.LifecycleListener
import com.solo4.core.mvi.decompose.ViewComponent
import org.koin.core.scope.Scope

class SettingsComponent(
    override val componentContext: ComponentContext,
    override val navigation: StackNavigation<NavTarget>,
) : ViewComponent<NavTarget>,
    ComponentContext by componentContext,
    LifecycleListener by DefaultLifecycleListener(componentContext.lifecycle) {

    override val scope: Scope by componentScope()

    @Composable
    fun Content(modifier: Modifier) {
        SettingsScreen(
            modifier = modifier,
            onBackPressed = navigation::pop
        )
    }

    override fun onDestroy() {
        scope.close()
    }
}
