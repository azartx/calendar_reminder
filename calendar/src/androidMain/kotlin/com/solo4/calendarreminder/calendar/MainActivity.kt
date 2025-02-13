package com.solo4.calendarreminder.calendar

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.bumble.appyx.navigation.integration.NodeActivity
import com.solo4.calendarreminder.calendar.nodes.root.RootComponent
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.kmputils.MultiplatformContext
import com.solo4.core.permissions.ExactAlarm
import com.solo4.core.permissions.Notifications
import com.solo4.core.permissions.PermissionsHandler
import com.solo4.core.permissions.getPermissionHandler
import com.solo4.domain.eventmanager.EventsNotificationManager
import com.solo4.domain.eventmanager.getEventsNotificationManager
import kotlinx.coroutines.launch

class MainActivity : NodeActivity() {

    companion object {
        lateinit var permissionHandler: PermissionsHandler
    }

    private val multiplatformContext = object : MultiplatformContext {
        private var _internal: MainActivity? = null
        override fun getContext() = _internal
        override fun setContext(context: Any?) {
            _internal = context as? MainActivity
        }

        override fun dispose() {
            _internal = null
        }
    }.apply { setContext(this@MainActivity) }

    private val eventNotificationManager: EventsNotificationManager = getEventsNotificationManager(
        multiplatformContext,
        getPlatformCalendar()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = RootComponent(defaultComponentContext())
        permissionHandler = getPermissionHandler(multiplatformContext)

        askPermissions()

        setContent {
            MaterialTheme {
                Children(rootComponent.stack) {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        when (val child = it.instance) {
                            else -> child.Content(Modifier)
                        }
                    }
                }
            }
        }
    }

    private fun askPermissions() {
        if (!permissionHandler.hasPermission(Notifications)) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    val isNotificationPermissionGranted =
                        permissionHandler.askPermission(Notifications)

                    if (!isNotificationPermissionGranted) {
                        Toast.makeText(
                            this@MainActivity,
                            "Уведомления запрещены",
                            Toast.LENGTH_LONG
                        ).show()
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