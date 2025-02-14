package com.solo4.calendarreminder.calendar

import android.app.Application
import com.solo4.calendarreminder.calendar.di.applicationModules
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this

        startKoin {
            modules(applicationModules)
        }
    }
}