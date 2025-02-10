package com.solo4.core.permissions

interface Permission {

    val name: String

    val kind: PermissionKind
}

expect object Notifications : Permission {

    override val name: String

    override val kind: PermissionKind
}

expect object ExactAlarm : Permission {

    override val name: String

    override val kind: PermissionKind
}