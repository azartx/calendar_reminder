package com.solo4.calendarreminder.utils.permissions

sealed class Permission(val permissionKind: PermissionKind = PermissionKind.Usual) {

    abstract val name: String

    data object Notifications : Permission() {
        override val name: String = "android.permission.POST_NOTIFICATIONS"
    }

    data object ExactAlarm : Permission(PermissionKind.System) {
        override val name: String = "android.settings.REQUEST_SCHEDULE_EXACT_ALARM"
    }
}

sealed interface PermissionKind {

    data object Usual : PermissionKind
    data object System : PermissionKind
}