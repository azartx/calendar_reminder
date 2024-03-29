package com.solo4.core.calendar
import java.time.YearMonth as JavaYearMonth

actual fun yearMonth(year: Int, month: Int): YearMonth {
    return JvmYearMonth(year, month)
}

internal class JvmYearMonth(
    override val year: Int,
    override val month: Int
) : YearMonth {

    private val ym = JavaYearMonth.of(year, month)

    override val days: Int = ym.lengthOfMonth()

    override fun dayOfWeekNumber(day: Int): Int {
        return ym.atDay(day).dayOfWeek.value
    }
}