package com.solo4.calendarreminder

import android.app.Application

class App : Application() {

    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}