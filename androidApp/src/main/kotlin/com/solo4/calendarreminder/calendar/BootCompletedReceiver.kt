package com.solo4.calendarreminder.calendar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.solo4.calendarreminder.calendar.domain.RestoreRemindersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext

private const val TAG = "BootCompletedReceiver"

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return

        val pendingResult = goAsync()
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        scope.launch {
            try {
                val koin = GlobalContext.getOrNull()
                if (koin == null) {
                    Log.e(TAG, "Koin is not started; cannot restore reminders")
                    return@launch
                }
                koin.get<RestoreRemindersUseCase>().invoke()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to restore reminders after boot", e)
            } finally {
                pendingResult.finish()
            }
        }
    }
}
