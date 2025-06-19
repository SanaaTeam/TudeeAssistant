package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<TasksScreenUiState>(TasksScreenUiState()) {


    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            categoryService.getCategories().collect { categoryList ->
                _state.update {
                    it.copy(
                        categories = categoryList.map { category -> category.toState() },
                        isLoading = false
                    )
                }
            }
        }
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
                                task.toState()
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTaskStatusSelectedChange(taskUiStatus: TaskUiStatus) {
        _state.update { it.copy(selectedTaskUiStatus = taskUiStatus) }
    }

    fun onTaskSelected(task: TaskUiState) {
        _state.update { it.copy(selectedTask = task) }
    }

    fun onTaskClick(task: TaskUiState) {
        onTaskSelected(task)
        onShowTaskDetailsDialogChange(true)
    }

    fun onTaskDeleted() {
        _state.value.selectedTask.let {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                runCatching {
                    if (it?.id == null) return@launch
                    taskService.deleteTaskById(it.id)
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
    }

    fun onDueDateChange(date: LocalDate) {
        _state.update {
            it.copy(selectedDate = date).also { Log.i("test", date.toString()) }
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

    fun onTaskSwipeToDelete(task: TaskUiState) {
        onTaskSelected(task)
        onShowDeleteDialogChange(true)
    }
}