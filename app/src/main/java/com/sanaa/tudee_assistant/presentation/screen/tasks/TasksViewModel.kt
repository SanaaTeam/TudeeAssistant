package com.sanaa.tudee_assistant.presentation.screen.tasks

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.state.mapper.toTask
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<TasksScreenUiState>(TasksScreenUiState()) {


    init {
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                _state.update {
                    it.copy(
                        categories = categoryList.map { category -> category.toState(0) }
                    )
                }

            }
        }
        getTasksByDueDate()
    }

    private var dateJob: Job? = null
    private fun getTasksByDueDate() {

        dateJob?.takeIf { it.isActive }?.cancel()

           dateJob =  viewModelScope.launch {
                taskService.getTasksByDueDate(_state.value.selectedDate)
                    .collect { taskList ->
                        _state.update {
                            it.copy(
                                currentDateTasks = taskList.map { task ->
                                    task.toState() },
                            )
                        }
                    }

        }
    }

    fun onTaskStatusSelectedChange(taskUiStatus: TaskUiStatus) {
        _state.update { it.copy(selectedStatusTab = taskUiStatus) }
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
                runCatching {
                    if (it?.id == null) return@launch
                    taskService.deleteTaskById(it.id)
                }.onSuccess {
                    handleOnSuccess(messageStringId = R.string.snack_bar_success)
                    getTasksByDueDate()
                }.onFailure {
                    handleOnError( messageStringId = R.string.snack_bar_error)

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
        _state.update { it.copy(showDeleteTaskBottomSheet = show) }
    }

    fun onShowTaskDetailsDialogChange(show: Boolean) {
        _state.update { it.copy(showTaskDetailsBottomSheet = show) }
    }

    fun onSnackBarShown() {
        _state.update {
            it.copy(successMessageStringId = null, errorMessageStringId = null)
        }
    }

    fun onTaskDeletedDismiss() {
        _state.update { it.copy(showDeleteTaskBottomSheet = false) }
    }

    fun onTaskSwipeToDelete(task: TaskUiState) {
        viewModelScope.launch {
            onTaskSelected(task)
            onShowDeleteDialogChange(true)
        }
    }

    fun onMoveTaskToAnotherStatus() {
        viewModelScope.launch {
            if (state.value.selectedTask == null) return@launch
            state.value.selectedTask?.copy(
                status = when (state.value.selectedStatusTab) {
                    TaskUiStatus.TODO -> TaskUiStatus.IN_PROGRESS
                    TaskUiStatus.IN_PROGRESS -> TaskUiStatus.DONE
                    TaskUiStatus.DONE -> TaskUiStatus.DONE
                }
            )?.let { updatedTask ->
                runCatching {
                    taskService.updateTask(
                        updatedTask.toTask()
                    )
                }.onSuccess {
                    handleOnSuccess(
                        messageStringId = R.string.snack_bar_success
                    )
                }.onFailure {
                    handleOnError()
                }
            }
        }
    }

    private fun handleOnSuccess(messageStringId: Int? = null) {
        _state.update {
            it.copy(
                successMessageStringId = messageStringId,
                errorMessageStringId = null,
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }

    private fun handleOnError(messageStringId: Int? = null) {
        _state.update {
            it.copy(
                successMessageStringId = null,
                errorMessageStringId = messageStringId,
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }
}