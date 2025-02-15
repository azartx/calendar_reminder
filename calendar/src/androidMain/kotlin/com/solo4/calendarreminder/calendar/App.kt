package com.solo4.calendarreminder.calendar

import android.app.Application
import com.solo4.calendarreminder.calendar.di.applyApplicationModules
import com.solo4.core.kmputils.MultiplatformContext
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var multiplatformContext: MultiplatformContext
    }

    override fun onCreate() {
        super.onCreate()
        multiplatformContext = createMultiplatformContext()

        startKoin {
            applyApplicationModules(multiplatformContext)
        }
    }

    private fun createMultiplatformContext(): MultiplatformContext {
        return object : MultiplatformContext {

            private var context: App? = null

            override fun getContext(): Any? {
                return context
            }

            override fun setContext(context: Any?) {
                this.context = context as? App
            }

            override fun dispose() {
                context = null
            }
        }.apply { setContext(context = this@App) }
    }
}