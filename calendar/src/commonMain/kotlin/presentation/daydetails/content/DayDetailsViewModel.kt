package com.solo4.calendarreminder.calendar.presentation.daydetails.content

import com.solo4.calendarreminder.calendar.data.repository.calendar.CalendarRepository
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.DATE_PATTERN
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.formatDateIdToDayMillis
import com.solo4.calendarreminder.calendar.presentation.calendar.content.utils.toDateByPattern
import com.solo4.calendarreminder.calendar.presentation.daydetails.content.state.DayDetailsScreenState
import com.solo4.core.calendar.CalendarWrapper
import com.solo4.core.mvi.decompose.ViewModel
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