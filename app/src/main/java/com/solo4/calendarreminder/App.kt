package com.solo4.calendarreminder

import android.app.Application
import com.solo4.calendarreminder.data.notifications.EventsNotificationManager
import com.solo4.calendarreminder.utils.calendar.AndroidCalendar
import com.solo4.calendarreminder.utils.calendar.CalendarWrapper

// TODO
// handle alarm manager permission
// handle notifications permission

class App : Application() {

    companion object {
        lateinit var app: App
        lateinit var eventsNotificationManager: EventsNotificationManager
        val calendarWrapper: CalendarWrapper by lazy { AndroidCalendar() }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        eventsNotificationManager = EventsNotificationManager(this)
        AndroidCalendar()
    }
}