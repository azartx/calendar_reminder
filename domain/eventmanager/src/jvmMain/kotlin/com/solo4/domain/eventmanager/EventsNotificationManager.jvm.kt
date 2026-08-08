package com.solo4.domain.eventmanager

import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.calendar.model.CalendarEvent
import com.solo4.core.kmputils.MultiplatformContext
import java.awt.Color
import java.awt.Image
import java.awt.SystemTray
import java.awt.Toolkit
import java.awt.TrayIcon
import java.awt.image.BufferedImage
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.swing.SwingUtilities

actual fun getEventsNotificationManager(
    context: MultiplatformContext,
    calendar: CalendarWrapper
): EventsNotificationManager {
    return JvmEventsNotificationManager(calendar)
}

internal class JvmEventsNotificationManager(
    private val calendar: CalendarWrapper,
) : EventsNotificationManager {

    private val scheduler = Executors.newSingleThreadScheduledExecutor { runnable ->
        Thread(runnable, "calendar-reminder-scheduler").apply { isDaemon = true }
    }
    private val scheduledTasks = ConcurrentHashMap<Int, ScheduledFuture<*>>()

    @Volatile
    private var trayIcon: TrayIcon? = null

    init {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                shutdown()
            },
        )
    }

    override fun scheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long,
    ) {
        if (event.eventId <= 0) return
        if (scheduleBeforeMillis <= 0L) return
        if (!canScheduleEvent()) return

        val triggerAt = event.eventTimeMillis - scheduleBeforeMillis
        val delayMillis = triggerAt - calendar.millisNow
        if (delayMillis <= 0L) return

        cancelEvent(event.eventId)

        val future = scheduler.schedule(
            {
                showNotification(event)
                scheduledTasks.remove(event.eventId)
            },
            delayMillis,
            TimeUnit.MILLISECONDS,
        )
        scheduledTasks[event.eventId] = future
    }

    override fun cancelEvent(eventId: Int) {
        if (eventId <= 0) return
        scheduledTasks.remove(eventId)?.cancel(false)
    }

    override fun rescheduleEvent(
        event: CalendarEvent,
        scheduleBeforeMillis: Long,
    ) {
        cancelEvent(event.eventId)
        if (scheduleBeforeMillis <= 0L) return
        scheduleEvent(event, scheduleBeforeMillis)
    }

    override fun restoreScheduledEvents(reminders: List<ScheduledReminder>) {
        reminders.forEach { reminder ->
            scheduleEvent(reminder.event, reminder.scheduleBeforeMillis)
        }
    }

    override fun canScheduleEvent(): Boolean {
        return SystemTray.isSupported()
    }

    private fun showNotification(event: CalendarEvent) {
        ensureTrayIcon()
        val icon = trayIcon ?: return
        val text = event.description.ifBlank { "Upcoming event" }
        SwingUtilities.invokeLater {
            icon.displayMessage(
                event.title.ifBlank { "Calendar Reminder" },
                text,
                TrayIcon.MessageType.INFO,
            )
        }
    }

    private fun ensureTrayIcon() {
        if (trayIcon != null || !SystemTray.isSupported()) return
        synchronized(this) {
            if (trayIcon != null) return
            try {
                Toolkit.getDefaultToolkit()
                val icon = TrayIcon(createTrayImage(), "Calendar Reminder").apply {
                    isImageAutoSize = true
                    toolTip = "Calendar Reminder"
                }
                SystemTray.getSystemTray().add(icon)
                trayIcon = icon
            } catch (_: Exception) {
                trayIcon = null
            }
        }
    }

    private fun createTrayImage(): Image {
        val size = 16
        val image = BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
        val g = image.createGraphics()
        try {
            g.color = Color(33, 150, 243)
            g.fillRoundRect(0, 0, size - 1, size - 1, 4, 4)
            g.color = Color.WHITE
            g.fillRect(3, 5, size - 6, size - 8)
            g.color = Color(33, 150, 243)
            g.fillRect(3, 5, size - 6, 3)
        } finally {
            g.dispose()
        }
        return image
    }

    private fun shutdown() {
        scheduledTasks.keys.toList().forEach(::cancelEvent)
        scheduler.shutdownNow()
        val icon = trayIcon ?: return
        trayIcon = null
        try {
            if (SystemTray.isSupported()) {
                SystemTray.getSystemTray().remove(icon)
            }
        } catch (_: Exception) {
            // Best-effort cleanup during JVM shutdown.
        }
    }
}
