package com.solo4.calendarreminder.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import com.solo4.calendarreminder.presentation.screens.calendar.factory.CalendarModelFactory
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentMonth
import com.solo4.calendarreminder.presentation.screens.calendar.utils.currentYear
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CalendarViewModel : ViewModel() {

    private val calendarFactory: CalendarModelFactory = CalendarModelFactory()

    private val _calendarModel = MutableStateFlow(calendarFactory.createMonthModels(currentYear, currentMonth))
    val calendarModel = _calendarModel.asStateFlow()
}