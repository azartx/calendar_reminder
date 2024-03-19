package com.solo4.calendarreminder.utils.permissions

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.solo4.calendarreminder.MainActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidPermissionsHandler(
    private var mainActivity: MainActivity?
) : PermissionsHandler, DefaultLifecycleObserver {

    init {
        mainActivity?.lifecycle?.addObserver(this)
            ?: throw Exception("Activity should be provided in constructor")
    }

    override fun hasPermission(permission: Permission): Boolean {
        return isGranted(mainActivity!!.checkSelfPermission(permission.name))
    }

    override suspend fun askPermission(permission: Permission): Boolean = suspendCancellableCoroutine { cont ->
        if (permission.permissionKind == PermissionKind.System) {
            askSystemPermission(permission)
            cont.resume(true)
        } else {
            mainActivity!!.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                cont.resume(isGranted)
            }
                .launch(permission.name)
        }
    }

    private fun askSystemPermission(permission: Permission) {
        if (permission is Permission.ExactAlarm) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                mainActivity!!.startActivity(
                    Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                )
            }
        }
    }

    private fun isGranted(permissionAskResult: Int): Boolean {
        return permissionAskResult == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy(owner: LifecycleOwner) {
        mainActivity = null
        owner.lifecycle.removeObserver(this)
        super.onDestroy(owner)
    }
}