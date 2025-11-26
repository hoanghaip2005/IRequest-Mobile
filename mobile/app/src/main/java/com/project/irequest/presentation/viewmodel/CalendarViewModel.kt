package com.project.irequest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.irequest.data.repository.CalendarRepository
import com.project.irequest.presentation.ui.calendar.CalendarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class CalendarViewModel : ViewModel() {

    private val calendarRepository = CalendarRepository.getInstance()

    private val _uiState = MutableStateFlow<CalendarUiState>(CalendarUiState.Loading)
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()

    private val _selectedDateEvents = MutableStateFlow<List<CalendarEvent>>(emptyList())
    val selectedDateEvents: StateFlow<List<CalendarEvent>> = _selectedDateEvents.asStateFlow()

    private val _monthEvents = MutableStateFlow<Map<LocalDate, List<CalendarEvent>>>(emptyMap())
    val monthEvents: StateFlow<Map<LocalDate, List<CalendarEvent>>> = _monthEvents.asStateFlow()

    /**
     * Load events for a specific month
     */
    fun loadMonthEvents(yearMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.value = CalendarUiState.Loading

            calendarRepository.getEventsByMonth(yearMonth.year, yearMonth.monthValue)
                .onSuccess { events ->
                    _monthEvents.value = events
                    _uiState.value = CalendarUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = CalendarUiState.Error(
                        error.message ?: "Failed to load events"
                    )
                }
        }
    }

    /**
     * Load events for a specific date
     */
    fun loadEventsByDate(date: LocalDate) {
        viewModelScope.launch {
            calendarRepository.getEventsByDate(date)
                .onSuccess { events ->
                    _selectedDateEvents.value = events
                }
                .onFailure { error ->
                    // Keep existing events on error
                    _selectedDateEvents.value = emptyList()
                }
        }
    }

    /**
     * Get events count for a specific date (for calendar grid indicators)
     */
    fun getEventsCountForDate(date: LocalDate): Int {
        return _monthEvents.value[date]?.size ?: 0
    }

    /**
     * Check if a date has events
     */
    fun hasEvents(date: LocalDate): Boolean {
        return _monthEvents.value[date]?.isNotEmpty() == true
    }
}

sealed class CalendarUiState {
    object Loading : CalendarUiState()
    object Success : CalendarUiState()
    data class Error(val message: String) : CalendarUiState()
}
