package com.solo4.calendarreminder

import android.app.Application
import com.solo4.calendarreminder.data.notifications.EventsNotificationManager
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.getPlatformCalendar

class App : Application() {

    companion object {
        lateinit var app: App
        lateinit var eventsNotificationManager: EventsNotificationManager
        val calendarWrapper: CalendarWrapper by lazy { getPlatformCalendar() }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        eventsNotificationManager = EventsNotificationManager(this, calendarWrapper)
    }
}