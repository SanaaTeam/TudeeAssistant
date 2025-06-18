package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService
) : ViewModel() {

    private val _state = MutableStateFlow(TasksScreenUiState())
    val uiState: StateFlow<TasksScreenUiState> = _state.asStateFlow()


    init {
        getTasksByDueDate()
    }

    private fun getTasksByDueDate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            taskService.getTasksByDueDate(_state.value.selectedDate)
                .collect { taskList ->
                    _state.update {
                        it.copy(
                            currentDateTasks = taskList.map { task ->
                                task.toUiModel(
                                    categoryImagePath = "task.category.imagePath"
                                )
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTaskStatusSelectedChange(taskStatus: TaskUiStatus) {
        _state.update { it.copy(selectedTaskUiStatus = taskStatus) }
    }

    fun onTaskSelected(task: TaskUiModel) {
        _state.update { it.copy(selectedTask = task) }
    }

    fun onTaskDeleted() {
        val selectedTask = _state.value.selectedTask ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                if (selectedTask.id == null) return@launch
                taskService.deleteTaskById(selectedTask.id)
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showDeleteDialog = false,
                        selectedTask = null,
                    )
                }
                getTasksByDueDate()
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showDeleteDialog = false,
                        selectedTask = null,
                    )
                }
            }
        }
    }

    fun onDueDateChange(date: LocalDate) {
        _state.update {
            it.copy(selectedDate = date)
        }
        getTasksByDueDate()
    }

    fun onShowDeleteDialogChange(show: Boolean) {
        _state.update { it.copy(showDeleteDialog = show) }
    }

    fun onShowTaskDetailsDialogChange(show: Boolean) {
        _state.update { it.copy(showTaskDetailsDialog = show) }
    }

    fun onShowEditDialogChange(show: Boolean) {
        _state.update { it.copy(showEditDialog = show) }
    }
}
