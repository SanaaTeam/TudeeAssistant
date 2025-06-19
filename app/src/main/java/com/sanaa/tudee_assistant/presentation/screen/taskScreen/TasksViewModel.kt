package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
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
        addFakeData()
    }

    fun addFakeData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                fakeTasks.forEach {
                    taskService.addTask(
                        it
                    )
                }
            }.onSuccess {
                Log.d("TaskViewModel", "addFakeDate: success")
            }
        }

    }

    fun getTaskById() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                taskService.getTaskById(1)
            }.onSuccess {
                Log.d(it.title.toString(), "getTaskById: ")
            }.onFailure {
            }
        }
    }


    val tasks = listOf(
        TaskUiModel(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE
        ),
        TaskUiModel(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        ),
    )

    private fun getTasksByDueDate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            taskService.getTasksByDueDate(_state.value.selectedDate)
                .collect { taskList ->
                    _state.update {
                        it.copy(
                            currentDateTasks = tasks,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTaskStatusSelectedChange(taskUiStatus: TaskUiStatus) {
        _state.update { it.copy(selectedTaskUiStatus = taskUiStatus) }
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

    fun onTaskSwipeToDelete(id: Int) {
        _state.update { it.copy(currentDateTasks = it.currentDateTasks.filterNot { item -> item.id == id }) }
    }
}
