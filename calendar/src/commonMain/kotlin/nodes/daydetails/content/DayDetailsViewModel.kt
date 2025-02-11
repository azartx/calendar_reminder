package com.solo4.calendarreminder.calendar.nodes.daydetails.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.DATE_PATTERN
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.calendar.nodes.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.calendar.nodes.daydetails.content.state.DayDetailsScreenState
import com.solo4.core.calendar.CalendarWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DayDetailsViewModel(
    private val repository: CalendarRepository,
    calendar: CalendarWrapper,
    val dayId: Long
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<DayDetailsScreenState>(DayDetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    val currentDate = calendar.formatDateIdToDayMillis(dayId)
        .toDateByPattern(DATE_PATTERN)

    init {
        viewModelScope.launch {
            loadDayEvents()
        }
    }

    private suspend fun loadDayEvents() {
        _screenState.emit(DayDetailsScreenState.Content(repository.getMonthEvents(dayId)))
    }
}