package com.solo4.domain.eventmanager.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class Event(
    val eventId: Int,
    val dayMillis: Long,
    val title: String,
    val description: String,
    val eventTimeMillis: Long
) : Parcelable
