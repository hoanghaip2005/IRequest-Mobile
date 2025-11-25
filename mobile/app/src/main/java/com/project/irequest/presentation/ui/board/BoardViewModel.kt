package com.project.irequest.presentation.ui.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.irequest.data.models.Request
import com.example.irequest.data.models.request.UpdateRequestModel
import com.example.irequest.data.repository.FirebaseRequestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BoardUiState(
    val isLoading: Boolean = false,
    val backlogRequests: List<Request> = emptyList(),
    val todoRequests: List<Request> = emptyList(),
    val inProgressRequests: List<Request> = emptyList(),
    val reviewRequests: List<Request> = emptyList(),
    val doneRequests: List<Request> = emptyList(),
    val error: String? = null
)

class BoardViewModel(
    private val requestRepository: FirebaseRequestRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BoardUiState())
    val uiState: StateFlow<BoardUiState> = _uiState.asStateFlow()

    init {
        loadRequests()
    }

    fun loadRequests() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // Load all requests (paginated, increase pageSize to get more)
                val result = requestRepository.getMyRequests(page = 1, pageSize = 100)
                
                result.onSuccess { requests ->
                    // Group requests by status
                    val backlog = requests.filter { 
                        it.statusName?.contains("Backlog", ignoreCase = true) == true ||
                        it.statusName?.contains("New", ignoreCase = true) == true
                    }
                    val todo = requests.filter { 
                        it.statusName?.contains("To Do", ignoreCase = true) == true ||
                        it.statusName?.contains("Pending", ignoreCase = true) == true
                    }
                    val inProgress = requests.filter { 
                        it.statusName?.contains("In Progress", ignoreCase = true) == true ||
                        it.statusName?.contains("Processing", ignoreCase = true) == true
                    }
                    val review = requests.filter { 
                        it.statusName?.contains("Review", ignoreCase = true) == true ||
                        it.statusName?.contains("Approval", ignoreCase = true) == true
                    }
                    val done = requests.filter { 
                        it.statusName?.contains("Done", ignoreCase = true) == true ||
                        it.statusName?.contains("Completed", ignoreCase = true) == true ||
                        it.statusName?.contains("Closed", ignoreCase = true) == true
                    }
                    
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        backlogRequests = backlog,
                        todoRequests = todo,
                        inProgressRequests = inProgress,
                        reviewRequests = review,
                        doneRequests = done
                    )
                }.onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = error.message ?: "Failed to load requests"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun moveRequest(requestId: Int, newStatusId: Int) {
        viewModelScope.launch {
            try {
                // Update request with new status
                val updateModel = UpdateRequestModel(
                    requestId = requestId,
                    statusId = newStatusId
                )
                requestRepository.updateRequest(updateModel)
                loadRequests()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to move request"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
