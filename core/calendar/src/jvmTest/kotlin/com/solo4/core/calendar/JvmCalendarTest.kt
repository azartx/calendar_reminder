package com.solo4.core.calendar

import java.util.Calendar
import java.util.TimeZone
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class JvmCalendarTest {

    private lateinit var previousTimeZone: TimeZone

    @BeforeTest
    fun setUp() {
        previousTimeZone = TimeZone.getDefault()
    }

    @AfterTest
    fun tearDown() {
        TimeZone.setDefault(previousTimeZone)
    }

    @Test
    fun millisOf_clearsSecondsAndMillis() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        val calendar = JvmCalendar()

        val millis = calendar.millisOf(2024, 7, 15)
        val parts = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = millis
        }

        assertEquals(0, parts.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, parts.get(Calendar.MINUTE))
        assertEquals(0, parts.get(Calendar.SECOND))
        assertEquals(0, parts.get(Calendar.MILLISECOND))
    }

    @Test
    fun datePickerRoundTrip_preservesLocalDayStart_inDstZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
        val calendar = JvmCalendar()

        // Summer (EDT, UTC-4)
        val summerLocal = calendar.millisOf(2024, 7, 15)
        assertEquals(summerLocal, calendar.fromDatePickerMillis(calendar.toDatePickerMillis(summerLocal)))

        // Winter (EST, UTC-5)
        val winterLocal = calendar.millisOf(2024, 1, 15)
        assertEquals(winterLocal, calendar.fromDatePickerMillis(calendar.toDatePickerMillis(winterLocal)))

        // DST spring-forward day (2024-03-10)
        val springLocal = calendar.millisOf(2024, 3, 10)
        assertEquals(springLocal, calendar.fromDatePickerMillis(calendar.toDatePickerMillis(springLocal)))
    }

    @Test
    fun toDatePickerMillis_usesUtcMidnightForSameCalendarDate() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
        val calendar = JvmCalendar()

        val local = calendar.millisOf(2024, 7, 15)
        val picker = calendar.toDatePickerMillis(local)

        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            timeInMillis = picker
        }
        assertEquals(2024, utc.get(Calendar.YEAR))
        assertEquals(Calendar.JULY, utc.get(Calendar.MONTH))
        assertEquals(15, utc.get(Calendar.DAY_OF_MONTH))
        assertEquals(0, utc.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, utc.get(Calendar.MINUTE))
        assertEquals(0, utc.get(Calendar.SECOND))
        assertEquals(0, utc.get(Calendar.MILLISECOND))
    }

    @Test
    fun timeZoneOffsetAt_reflectsDst() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
        val calendar = JvmCalendar()

        val winterOffset = calendar.timeZoneOffsetAt(calendar.millisOf(2024, 1, 15))
        val summerOffset = calendar.timeZoneOffsetAt(calendar.millisOf(2024, 7, 15))

        // EST = -5h, EDT = -4h
        assertEquals(-5 * 60 * 60 * 1000, winterOffset)
        assertEquals(-4 * 60 * 60 * 1000, summerOffset)
    }

    @Test
    fun eventTime_fromDatePickerPlusHourMinute_matchesLocalWallClock() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"))
        val calendar = JvmCalendar()

        val pickerUtcMidnight = calendar.toDatePickerMillis(calendar.millisOf(2024, 7, 15))
        val localDayStart = calendar.fromDatePickerMillis(pickerUtcMidnight)
        val eventTime = localDayStart + (15 * 60 * 60 * 1000L) + (30 * 60 * 1000L) // 15:30

        val local = Calendar.getInstance().apply { timeInMillis = eventTime }
        assertEquals(2024, local.get(Calendar.YEAR))
        assertEquals(Calendar.JULY, local.get(Calendar.MONTH))
        assertEquals(15, local.get(Calendar.DAY_OF_MONTH))
        assertEquals(15, local.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, local.get(Calendar.MINUTE))
    }
}
