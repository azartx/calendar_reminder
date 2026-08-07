package com.solo4.core.permissions

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.solo4.core.kmputils.MultiplatformContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

actual fun getPermissionHandler(context: MultiplatformContext): PermissionsHandler {
    return AndroidPermissionsHandler(context)
}

class AndroidPermissionsHandler(
    private var context: MultiplatformContext?
) : PermissionsHandler, DefaultLifecycleObserver {

    private val activity: ComponentActivity
        get() = context?.getContext() as ComponentActivity

    private var notificationPermissionContinuation: CancellableContinuation<Boolean>? = null

    private val notificationPermissionLauncher =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            notificationPermissionContinuation?.resume(isGranted)
            notificationPermissionContinuation = null
        }

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun hasPermission(permission: Permission): Boolean {
        return when (permission) {
            Notifications -> hasNotificationsPermission()
            ExactAlarm -> hasExactAlarmPermission()
            else -> {
                ContextCompat.checkSelfPermission(activity, permission.name) ==
                    PackageManager.PERMISSION_GRANTED
            }
        }
    }

    override suspend fun askPermission(permission: Permission): Boolean {
        return when (permission) {
            ExactAlarm -> {
                askExactAlarmPermission()
                // Settings screen is async; caller should re-check on resume.
                hasExactAlarmPermission()
            }

            Notifications -> askNotificationsPermission()

            else -> {
                if (hasPermission(permission)) true
                else askRuntimePermission(permission.name)
            }
        }
    }

    private fun hasNotificationsPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun hasExactAlarmPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return true
        val alarmManager = activity.getSystemService(AlarmManager::class.java) ?: return false
        return alarmManager.canScheduleExactAlarms()
    }

    private suspend fun askNotificationsPermission(): Boolean {
        if (hasNotificationsPermission()) return true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return askRuntimePermission(Manifest.permission.POST_NOTIFICATIONS)
    }

    private fun askExactAlarmPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
        if (hasExactAlarmPermission()) return
        activity.startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
    }

    private suspend fun askRuntimePermission(permissionName: String): Boolean {
        return suspendCancellableCoroutine { cont ->
            notificationPermissionContinuation = cont
            cont.invokeOnCancellation { notificationPermissionContinuation = null }
            notificationPermissionLauncher.launch(permissionName)
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        notificationPermissionContinuation?.cancel()
        notificationPermissionContinuation = null
        context?.dispose()
        context = null
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }
}
