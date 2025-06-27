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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val stringProvider: StringProvider,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState(), dispatcher),
    HomeScreenInteractionsListener {
    init {
        loadScreen()
        getTasks()
    }

    private fun loadScreen() {
        tryToExecute(
            callee = {
                preferencesManager.isDarkTheme.collect { isDarkTheme ->
                    updateState { it.copy(isDarkTheme = isDarkTheme) }
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
                    updateState { state -> state.copy(tasks = tasks.toStateList()) }

                    categoryService.getCategories().collect { categories ->
                        updateState { state ->
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
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskAddedSuccess)
            )
        }
    }

    override fun onAddTaskError(errorMessage: String) {
        updateState {
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
        updateState {
            it.copy(
                showEditTaskSheet = true,
                taskToEdit = taskToEdit,
                selectedTask = null
            )
        }
    }

    override fun onHideEditTaskSheet() {
        updateState { it.copy(showEditTaskSheet = false, taskToEdit = null) }
    }

    override fun onEditTaskSuccess() {
        getTasks()
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskUpdateSuccess)
            )
        }
    }

    override fun onEditTaskError(errorMessage: String) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(errorMessage)
            )
        }
    }

    override fun onHideSnackBar() {
        updateState {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onTaskClick(taskUiState: TaskUiState) {
        updateState { it.copy(selectedTask = taskUiState, showTaskDetailsBottomSheet = true) }
    }

    override fun onShowTaskDetails(show: Boolean) {
        updateState { it.copy(selectedTask = null) }
        updateState { it.copy(showTaskDetailsBottomSheet = show) }
    }

    override fun onShowAddTaskSheet() {
        updateState { it.copy(showAddTaskSheet = true) }
    }

    override fun onHideAddTaskSheet() {
        updateState { it.copy(showAddTaskSheet = false) }
    }

    override fun onMoveStatusSuccess() {
        getTasks()
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateSuccess),
                showTaskDetailsBottomSheet = false,
            )
        }
    }

    override fun onMoveStatusFail() {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateError)
            )
        }
    }

    private fun showErrorMessage(exception: Exception) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(
                    exception.message ?: stringProvider.unknownError
                )
            )
        }
    }
}