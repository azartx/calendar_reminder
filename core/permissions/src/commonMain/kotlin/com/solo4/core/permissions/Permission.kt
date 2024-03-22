package com.solo4.core.permissions

interface Permission {

    val name: String

    val kind: PermissionKind
}

expect object Notifications : Permission

expect object ExactAlarm : Permission