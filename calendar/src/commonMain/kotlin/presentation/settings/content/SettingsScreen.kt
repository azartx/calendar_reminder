package com.solo4.calendarreminder.calendar.presentation.settings.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.solo4.core.uicomponents.Toolbar

@Composable
fun SettingsScreen(
    modifier: Modifier,
    onBackPressed: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Toolbar(
            title = "Settings",
            onBackPressed = onBackPressed
        )
    }
}
