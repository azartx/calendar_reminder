package com.solo4.core.permissions

import com.solo4.core.kmputils.MultiplatformContext

expect fun getPermissionHandler(context: MultiplatformContext): PermissionsHandler

interface PermissionsHandler {

    fun hasPermission(permission: Permission): Boolean

    suspend fun askPermission(permission: Permission): Boolean
}