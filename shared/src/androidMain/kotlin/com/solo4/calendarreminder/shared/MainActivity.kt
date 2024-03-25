package com.solo4.calendarreminder.shared

import android.app.AlertDialog
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
import com.solo4.calendarreminder.presentation.navigation.AppNavigation
import com.solo4.calendarreminder.shared.theme.CalendarReminderTheme
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.kmputils.MultiplatformContext
import com.solo4.core.permissions.ExactAlarm
import com.solo4.core.permissions.Notifications
import com.solo4.core.permissions.PermissionsHandler
import com.solo4.core.permissions.getPermissionHandler
import com.solo4.domain.eventmanager.EventsNotificationManager
import com.solo4.domain.eventmanager.getEventsNotificationManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object {
        lateinit var permissionHandler: PermissionsHandler
    }

    private val multiplatformContext = object : MultiplatformContext {
        private var _internal: MainActivity? = null
        override fun getContext() = _internal
        override fun setContext(context: Any?) { _internal = context as? MainActivity }
        override fun dispose() { _internal = null }
    }.apply { setContext(this@MainActivity) }

    private val eventNotificationManager: EventsNotificationManager = getEventsNotificationManager(
        multiplatformContext,
        getPlatformCalendar()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHandler = getPermissionHandler(multiplatformContext)

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
        if (!permissionHandler.hasPermission(Notifications)) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    val isNotificationPermissionGranted = permissionHandler.askPermission(Notifications)

                    if (!isNotificationPermissionGranted) {
                        Toast.makeText(this@MainActivity, "Уведомления запрещены", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        if (!eventNotificationManager.canScheduleEvent()) {
            AlertDialog.Builder(this)
                .setTitle("Alarm manager permission")
                .setMessage("To ensure timely notifications of events you add, you need to grant permission to wake up the app.")
                .setPositiveButton(android.R.string.ok) { d, _ ->
                    lifecycleScope.launch {
                        permissionHandler.askPermission(ExactAlarm)
                    }
                    d.dismiss()
                }
        }
    }
}