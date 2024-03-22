package com.solo4.core.permissions

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.solo4.core.kmputils.MultiplatformContext
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

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun hasPermission(permission: Permission): Boolean {
        return isGranted(activity.checkSelfPermission(permission.name))
    }

    override suspend fun askPermission(permission: Permission): Boolean = suspendCancellableCoroutine { cont ->
        if (permission.kind == PermissionKind.System) {
            askSystemPermission(permission)
            cont.resume(true)
        } else {
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                cont.resume(isGranted)
            }
                .launch(permission.name)
        }
    }

    private fun askSystemPermission(permission: Permission) {
        if (permission is ExactAlarm) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                activity.startActivity(
                    Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                )
            }
        }
    }

    private fun isGranted(permissionAskResult: Int): Boolean {
        return permissionAskResult == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy(owner: LifecycleOwner) {
        context?.dispose()
        context = null
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }
}