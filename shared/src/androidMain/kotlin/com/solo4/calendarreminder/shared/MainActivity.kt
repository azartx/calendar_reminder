package com.solo4.calendarreminder.shared

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumble.appyx.navigation.integration.NodeComponentActivity
import com.bumble.appyx.navigation.integration.NodeHost
import com.bumble.appyx.navigation.platform.AndroidLifecycle
import com.solo4.calendarreminder.shared.nodes.root.RootNode
import com.solo4.core.calendar.getPlatformCalendar
import com.solo4.core.kmputils.MultiplatformContext
import com.solo4.core.permissions.ExactAlarm
import com.solo4.core.permissions.Notifications
import com.solo4.core.permissions.PermissionsHandler
import com.solo4.core.permissions.getPermissionHandler
import com.solo4.domain.eventmanager.EventsNotificationManager
import com.solo4.domain.eventmanager.getEventsNotificationManager
import kotlinx.coroutines.launch

class MainActivity : NodeComponentActivity() {

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

    private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionHandler = getPermissionHandler(multiplatformContext)

        askPermissions()

        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CompositionLocalProvider(LocalNavigator provides navigator) {
                        NodeHost(
                            lifecycle = AndroidLifecycle(lifecycle),
                            integrationPoint = appyxV2IntegrationPoint
                        ) { nodeContext ->
                            RootNode(nodeContext = nodeContext)
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