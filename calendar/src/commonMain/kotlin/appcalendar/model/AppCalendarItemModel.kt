package appcalendar.model

import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.getFormattedDateId

data class AppCalendarItemModel(
    val day: Int,
    val month: Int,
    val year: Int,
    val dayOfWeek: Int,
    val hasEvents: Boolean = false
) {
    val dayId: Long = getFormattedDateId(day, month, year)
}