package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toStateList
import com.sanaa.tudee_assistant.presentation.model.mapper.toTaskStatus
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val stringProvider: StringProvider,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState()), HomeScreenInteractionsListener {
    init {
        loadScreen()
        getTasks()
    }

    private fun loadScreen() {
        tryToExecute(
            callee = {
                preferencesManager.isDarkTheme.collect { isDarkTheme ->
                    _state.update { it.copy(isDarkTheme = isDarkTheme) }
                }
            },
            onError = ::showErrorMessage
        )
    }

    private fun getTasks() {
        tryToExecute(
            callee = {
                val today = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                taskService.getTasksByDueDate(today).collectLatest { tasks ->
                    _state.update { state -> state.copy(tasks = tasks.toStateList()) }

                    categoryService.getCategories().collect { categories ->
                        _state.update { state ->
                            state.copy(categories = categories.toStateList(tasks.size))
                        }
                    }
                }
            },
            onError = ::showErrorMessage
        )
    }

    override fun onToggleColorTheme() {
        tryToExecute(
            callee = {
                preferencesManager.setDarkTheme(state.value.isDarkTheme.not())
            },
            onError = ::showErrorMessage
        )
    }

    override fun onAddTaskSuccess() {
        getTasks()
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskAddedSuccess)
            )
        }
    }

    override fun onAddTaskError(errorMessage: String) {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(errorMessage)
            )
        }
    }

    override fun onNavigateToTaskScreen(status: TaskUiStatus) {
        tryToExecute(
            callee = {
                preferencesManager.changeTaskStatus(status.toTaskStatus())
            }
        )
    }

    override fun onShowEditTaskSheet(taskToEdit: TaskUiState) {
        _state.update {
            it.copy(
                showEditTaskSheet = true,
                taskToEdit = taskToEdit,
                selectedTask = null
            )
        }
    }

    override fun onHideEditTaskSheet() {
        _state.update { it.copy(showEditTaskSheet = false, taskToEdit = null) }
    }

    override fun onEditTaskSuccess() {
        getTasks()
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskUpdateSuccess)
            )
        }
    }

    override fun onEditTaskError(errorMessage: String) {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(errorMessage)
            )
        }
    }

    override fun onHideSnackBar() {
        _state.update {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onTaskClick(taskUiState: TaskUiState) {
        _state.update { it.copy(selectedTask = taskUiState, showTaskDetailsBottomSheet = true) }
    }

    override fun onShowTaskDetails(show: Boolean) {
        _state.update { it.copy(selectedTask = null) }
        _state.update { it.copy(showTaskDetailsBottomSheet = show) }
    }

    override fun onShowAddTaskSheet() {
        _state.update { it.copy(showAddTaskSheet = true) }
    }

    override fun onHideAddTaskSheet() {
        _state.update { it.copy(showAddTaskSheet = false) }
    }

    override fun onMoveStatusSuccess() {
        getTasks()
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateSuccess),
                showTaskDetailsBottomSheet = false,
            )
        }
    }

    override fun onMoveStatusFail() {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateError)
            )
        }
    }

    private fun showErrorMessage(exception: Exception) {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(
                    exception.message ?: stringProvider.unknownError
                )
            )
        }
    }
}