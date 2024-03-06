package com.solo4.calendarreminder.utils.permissions

import android.content.pm.PackageManager
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
        mainActivity!!.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            cont.resume(isGranted)
        }
            .launch(permission.name)
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