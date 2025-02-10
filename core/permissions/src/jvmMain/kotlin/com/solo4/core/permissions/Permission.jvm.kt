package com.solo4.core.permissions

actual object Notifications : Permission {

    actual override val name: String = "android.permission.POST_NOTIFICATIONS"

    actual override val kind: PermissionKind = PermissionKind.Usual
}
actual object ExactAlarm : Permission {

    actual override val name: String = "android.settings.REQUEST_SCHEDULE_EXACT_ALARM"

    actual override val kind: PermissionKind = PermissionKind.System
}