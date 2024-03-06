package com.solo4.calendarreminder.utils.permissions

import android.Manifest

sealed interface Permission {

    val name: String

    data object Notifications : Permission { override val name: String = Manifest.permission.POST_NOTIFICATIONS }
}