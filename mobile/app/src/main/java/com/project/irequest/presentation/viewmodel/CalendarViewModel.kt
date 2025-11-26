package com.project.irequest.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.irequest.data.repository.CalendarRepository
import com.project.irequest.presentation.ui.calendar.CalendarRequest
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

    private val _selectedDateRequests = MutableStateFlow<List<CalendarRequest>>(emptyList())
    val selectedDateRequests: StateFlow<List<CalendarRequest>> = _selectedDateRequests.asStateFlow()

    private val _monthRequests = MutableStateFlow<Map<LocalDate, List<CalendarRequest>>>(emptyMap())
    val monthRequests: StateFlow<Map<LocalDate, List<CalendarRequest>>> = _monthRequests.asStateFlow()

    /**
     * Load requests for a specific month
     */
    fun loadMonthRequests(yearMonth: YearMonth) {
        viewModelScope.launch {
            _uiState.value = CalendarUiState.Loading

            calendarRepository.getRequestsByMonth(yearMonth.year, yearMonth.monthValue)
                .onSuccess { requests ->
                    _monthRequests.value = requests
                    _uiState.value = CalendarUiState.Success
                }
                .onFailure { error ->
                    _uiState.value = CalendarUiState.Error(
                        error.message ?: "Failed to load requests"
                    )
                }
        }
    }

    /**
     * Load requests for a specific date
     */
    fun loadRequestsByDate(date: LocalDate) {
        viewModelScope.launch {
            calendarRepository.getRequestsByDate(date)
                .onSuccess { requests ->
                    _selectedDateRequests.value = requests
                }
                .onFailure { error ->
                    // Keep existing requests on error
                    _selectedDateRequests.value = emptyList()
                }
        }
    }

    /**
     * Get requests count for a specific date (for calendar grid indicators)
     */
    fun getRequestsCountForDate(date: LocalDate): Int {
        return _monthRequests.value[date]?.size ?: 0
    }

    /**
     * Check if a date has requests
     */
    fun hasRequests(date: LocalDate): Boolean {
        return _monthRequests.value[date]?.isNotEmpty() == true
    }
}

sealed class CalendarUiState {
    object Loading : CalendarUiState()
    object Success : CalendarUiState()
    data class Error(val message: String) : CalendarUiState()
}
