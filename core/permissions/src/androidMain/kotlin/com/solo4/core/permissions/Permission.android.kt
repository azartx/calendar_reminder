package com.solo4.core.permissions

actual object Notifications : Permission {

    override val name: String = "android.permission.POST_NOTIFICATIONS"

    override val kind: PermissionKind = PermissionKind.Usual
}
actual object ExactAlarm : Permission {

    override val name: String = "android.settings.REQUEST_SCHEDULE_EXACT_ALARM"

    override val kind: PermissionKind = PermissionKind.System
}