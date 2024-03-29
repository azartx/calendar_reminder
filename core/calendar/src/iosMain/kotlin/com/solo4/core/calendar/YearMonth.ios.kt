package com.solo4.core.calendar

actual fun yearMonth(year: Int, month: Int): YearMonth {
    return IosYearMonth(year, month)
}

internal class IosYearMonth(
    override val year: Int,
    override val month: Int
) : YearMonth {

    override val days: Int = 29

    override fun dayOfWeekNumber(day: Int): Int {
        return 1
    }
}