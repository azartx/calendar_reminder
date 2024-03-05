package com.solo4.calendarreminder

import android.app.Application
import com.solo4.calendarreminder.data.notifications.EventsNotificationManager

// TODO
// handle alarm manager permission
// handle notifications permission

class App : Application() {

    companion object {
        lateinit var app: App
        lateinit var eventsNotificationManager: EventsNotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        eventsNotificationManager = EventsNotificationManager(this)
    }
}