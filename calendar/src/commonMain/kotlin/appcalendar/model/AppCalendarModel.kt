package appcalendar.model

data class AppCalendarModel(
    val rows: List<AppCalendarRow>,
    val modelFormattedDate: String,
    val dayNow: Int,
    val yearNow: Int,
    val monthNow: Int
)