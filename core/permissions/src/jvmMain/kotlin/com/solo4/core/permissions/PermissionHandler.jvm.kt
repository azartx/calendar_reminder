package com.solo4.core.permissions

import com.solo4.core.kmputils.MultiplatformContext

actual fun getPermissionHandler(context: MultiplatformContext): PermissionsHandler {
    return JvmPermissionHandler(context)
}

class JvmPermissionHandler(context: MultiplatformContext) : PermissionsHandler {

    override fun hasPermission(permission: Permission): Boolean {
        return true
    }

    override suspend fun askPermission(permission: Permission): Boolean {
        return true
    }
}