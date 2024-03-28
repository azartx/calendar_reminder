package com.solo4.calendarreminder.shared.nodes.root

import com.bumble.appyx.utils.multiplatform.Parcelable
import com.bumble.appyx.utils.multiplatform.Parcelize

sealed class NavTarget : Parcelable {
    @Parcelize
    data object CalendarScreen : NavTarget()

    @Parcelize
    data object DayDetailsScreen : NavTarget()

    @Parcelize
    data object EventDetailsScreen : NavTarget()
}