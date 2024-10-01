package com.solo4.calendarreminder.utils

import android.content.Intent
import android.os.Build
import java.io.Serializable

inline fun <reified T : Serializable> Intent?.serializableExtra(key: String): T? {
    this ?: return null
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getSerializableExtra(key, T::class.java)
    } else {
        getSerializableExtra(key) as T
    }
}