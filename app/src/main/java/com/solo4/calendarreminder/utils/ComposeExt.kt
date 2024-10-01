package com.solo4.calendarreminder.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.solo4.core.calendar.model.Millis

@OptIn(ExperimentalMaterial3Api::class)
val TimePickerState.millis: Long
    get() {
        val hourMillis = hour * Millis.HOUR_1.millis
        val minutesMillis = minute * Millis.MINUTES_1.millis

        return hourMillis + minutesMillis
    }