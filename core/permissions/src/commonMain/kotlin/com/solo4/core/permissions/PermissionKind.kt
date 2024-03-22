package com.solo4.core.permissions

sealed interface PermissionKind {

    data object Usual : PermissionKind
    data object System : PermissionKind
}