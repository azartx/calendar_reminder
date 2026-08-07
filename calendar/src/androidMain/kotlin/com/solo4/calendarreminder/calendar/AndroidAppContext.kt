package com.solo4.calendarreminder.calendar

import android.content.Context

object AndroidAppContext {
    lateinit var application: Context
        private set

    fun init(context: Context) {
        application = context.applicationContext
    }
}
