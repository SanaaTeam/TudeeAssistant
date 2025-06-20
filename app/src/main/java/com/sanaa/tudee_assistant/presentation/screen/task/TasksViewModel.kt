package com.sanaa.tudee_assistant.presentation.screen.task

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.screen.task.mapper.toTask
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


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
                        isLoading = false
                    )
                }
            }
        }
        getTasksByDueDate()
        addFakeTask()
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


    fun addFakeTask() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                taskService.addTask(
                    Task(
                        id = 2,
                        title = "Organize ",
                        description = "Hello world ",
                        status = Task.TaskStatus.TODO,
                        dueDate = LocalDate(2025, 6, 19),
                        priority = Task.TaskPriority.HIGH,
                        categoryId = 1,
                        createdAt = Clock.System.now()
                            .toLocalDateTime(TimeZone.UTC),
                    )
                )
            }

        }
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
        viewModelScope.launch {
            onTaskSelected(task)
            onShowDeleteDialogChange(true)
        }
    }

    fun onEditTask(taskUiState: TaskUiState) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching {
                taskService.updateTask(
                    taskUiState.toTask(
                        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
            }.onSuccess {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showEditDialog = false,
                        selectedTask = null,
                    )
                }
                getTasksByDueDate()
            }.onFailure {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showEditDialog = false,
                        selectedTask = null,
                    )
                }
            }
        }
    }

    fun onTaskDeletedDismiss() {
        _state.update { it.copy(showDeleteDialog = false) }
    }

    fun onMoveTaskToAnotherStatus() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            if (state.value.selectedTask == null) return@launch
            state.value.selectedTask?.copy(
                status = when (state.value.selectedTaskUiStatus) {
                    TaskUiStatus.DONE -> TaskUiStatus.DONE
                    TaskUiStatus.TODO -> TaskUiStatus.IN_PROGRESS
                    TaskUiStatus.IN_PROGRESS -> TaskUiStatus.DONE
                }
            )?.let { updatedTask ->
                runCatching {
                    taskService.updateTask(
                        updatedTask.toTask(
                            Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        )
                    )
                }.onSuccess {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            showTaskDetailsDialog = false,
                            selectedTask = null,
                        )
                    }
                }
            }
        }
    }
}