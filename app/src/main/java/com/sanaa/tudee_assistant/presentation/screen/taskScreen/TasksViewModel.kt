package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper.toTask
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper.toUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
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
    lateinit var categories: List<Category>

    init {
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                categories = categoryList
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
                                task.toUiModel(
                                    categories.firstOrNull { category ->
                                        category.id == task.categoryId
                                    }?.imagePath ?: ""
                                )
                            },
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onTaskSelected(task: TaskUiModel) {
        _state.update { it.copy(selectedTask = task) }
    }

    fun onTaskClick(task: TaskUiModel) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            onShowTaskDetailsDialogChange(true)
            onTaskSelected(task)
        }
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
                    handleOnSuccess(message = R.string.snack_bar_success.toString())
                    getTasksByDueDate()
                }.onFailure {
                    handleOnError(message = R.string.snack_bar_error.toString())

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

    fun onSnackBarShown() {
        _state.update {
            it.copy(successMessage = null, errorMessage = null)
        }
    }

    fun onShowEditDialogChange(show: Boolean) {
        _state.update { it.copy(showEditDialog = show) }
    }

    fun onTaskDeletedDismiss() {
        _state.update { it.copy(showDeleteDialog = false) }
    }

    fun onTaskSwipeToDelete(task: TaskUiModel) {
        viewModelScope.launch {
            onTaskSelected(task)
            onShowDeleteDialogChange(true)
        }
    }

    fun onMoveTaskToAnotherStatus() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            if (state.value.selectedTask == null) return@launch
            state.value.selectedTask?.copy(
                status = when (state.value.selectedTaskUiStatus) {
                    TaskUiStatus.TODO -> TaskUiStatus.IN_PROGRESS
                    TaskUiStatus.IN_PROGRESS -> TaskUiStatus.DONE
                    TaskUiStatus.DONE -> TaskUiStatus.DONE
                }
            )?.let { updatedTask ->
                runCatching {
                    taskService.updateTask(updatedTask.toTask())
                }.onSuccess {
                    handleOnSuccess(
                        message = R.string.snack_bar_success.toString()
                    )
                }.onFailure {
                    handleOnError()
                }
            }
        }
    }

    private fun handleOnSuccess(message: String? = null) {
        _state.update {
            it.copy(
                successMessage = message,
                errorMessage = null,
                isLoading = false,
                showTaskDetailsDialog = false,
                showDeleteDialog = false
            )
        }
    }

    private fun handleOnError(message: String? = null) {
        _state.update {
            it.copy(
                successMessage = null,
                errorMessage = message,
                isLoading = false,
                showTaskDetailsDialog = false,
                showDeleteDialog = false
            )
        }
    }
}
