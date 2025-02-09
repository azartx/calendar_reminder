package appcalendar.model

import appcalendar.model.AppCalendarRow

data class AppCalendarModel(
    val rows: List<AppCalendarRow>,
    val modelFormattedDate: String,
    val dayNow: Int,
    val yearNow: Int,
    val monthNow: Int
)