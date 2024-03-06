package com.solo4.calendarreminder

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.solo4.calendarreminder.presentation.navigation.AppNavigation
import com.solo4.calendarreminder.presentation.theme.CalendarReminderTheme
import com.solo4.calendarreminder.utils.permissions.AndroidPermissionsHandler
import com.solo4.calendarreminder.utils.permissions.Permission
import com.solo4.calendarreminder.utils.permissions.PermissionsHandler
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var permissionHandler: PermissionsHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHandler = AndroidPermissionsHandler(this)

        askPermissions()

        setContent {
            CalendarReminderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(rememberNavController())
                }
            }
        }
    }

    private fun askPermissions() {
        if (!permissionHandler.hasPermission(Permission.Notifications)) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    val isNotificationPermissionGranted = permissionHandler.askPermission(Permission.Notifications)

                    if (!isNotificationPermissionGranted) {
                        Toast.makeText(this@MainActivity, "Уведомления запрещены", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}