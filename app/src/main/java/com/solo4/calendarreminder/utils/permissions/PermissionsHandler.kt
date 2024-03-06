package com.solo4.calendarreminder.utils.permissions

interface PermissionsHandler {

    fun hasPermission(permission: Permission): Boolean

    suspend fun askPermission(permission: Permission): Boolean
}