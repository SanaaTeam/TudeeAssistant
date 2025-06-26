package com.sanaa.tudee_assistant.presentation.screen.tasks

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toStateList
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate


class TaskViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val selectedStatusTab: TaskUiStatus,
    private val stringProvider: StringProvider,
) : BaseViewModel<TasksScreenUiState>(TasksScreenUiState()), TaskInteractionListener {
    init {
        updateState { it.copy(selectedStatusTab = selectedStatusTab) }
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                updateState {
                    it.copy(categories = categoryList.toStateList(0))
                }


            }
        }
        getTasksByDueDate()
    }

    private var dateJob: Job? = null
    private fun getTasksByDueDate() {

        dateJob?.takeIf { it.isActive }?.cancel()

        dateJob = viewModelScope.launch {
            taskService.getTasksByDueDate(_state.value.selectedDate).collect { taskList ->
                updateState { it.copy(currentDateTasks = taskList.toStateList()) }
            }

        }
    }


    private fun onTaskSelected(task: TaskUiState) {
        updateState { it.copy(selectedTask = task) }
    }

    override fun onTaskClicked(task: TaskUiState) {
        onTaskSelected(task)
        onDismissTaskDetails(true)
    }

    override fun onDeleteTask() {
        _state.value.selectedTask.let {
            viewModelScope.launch {
                runCatching {
                    if (it?.id == null) return@launch
                    taskService.deleteTaskById(it.id)
                }.onSuccess {
                    handleOnSuccess(message = stringProvider.taskDeleteSuccess)
                    getTasksByDueDate()
                }.onFailure {
                    handleOnError(message = stringProvider.unknownError)

                }
            }
        }
    }

    override fun onDateSelected(date: LocalDate) {
        updateState {
            it.copy(selectedDate = date)
        }
        getTasksByDueDate()
    }

    fun onShowDeleteDialogChange(show: Boolean) {
        updateState { it.copy(showDeleteTaskBottomSheet = show) }
    }

    override fun onDismissTaskDetails(show: Boolean) {
        updateState { it.copy(showTaskDetailsBottomSheet = show) }
    }


    override fun onAddTaskSuccess() {
        handleOnSuccess(message = stringProvider.taskAddedSuccess)
    }

    override fun onEditTaskSuccess() {
        handleOnSuccess(message = stringProvider.taskUpdateSuccess)
    }

    override fun onDeleteDismiss() {
        updateState { it.copy(showDeleteTaskBottomSheet = false) }
    }

    override fun onTaskSwipeToDelete(task: TaskUiState): Boolean {
        viewModelScope.launch {
            onTaskSelected(task)
            onShowDeleteDialogChange(true)
        }
        return false
    }


    override fun handleOnMoveToStatusSuccess() {
        handleOnSuccess(message = stringProvider.taskStatusUpdateSuccess)
    }

    override fun handleOnMoveToStatusFail() {
        handleOnError()
    }

    override fun onHideSnakeBar() {
        updateState {
            it.copy(snackBarState = SnackBarState())
        }
    }


    private fun handleOnSuccess(message: String? = null) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(
                    message ?: stringProvider.unknownError
                ),
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }

    private fun handleOnError(message: String? = stringProvider.unknownError) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(
                    message ?: stringProvider.unknownError
                ),
                showTaskDetailsBottomSheet = false,
                showDeleteTaskBottomSheet = false
            )
        }
    }
}