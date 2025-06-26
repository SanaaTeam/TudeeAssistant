package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.mapper.toStateList
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val stringProvider: StringProvider,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState()),
    HomeScreenInteractionsListener {

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
                taskService.getTasksByDueDate(today).collect { tasks ->
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
                snackBarState = SnackBarState.getInstance(stringProvider.getString(R.string.task_add_success))
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

    override fun onEditTaskSuccess() {
        getTasks()
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.getString(R.string.task_edited_successfully))
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
        _state.update { it.copy(selectedTask = taskUiState) }
    }

    override fun onDismissTaskDetails() {
        _state.update { it.copy(selectedTask = null) }
    }

    override fun onMoveStatusSuccess() {
        getTasks()
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.getString(R.string.task_status_update_success))
            )
        }
    }

    override fun onMoveStatusFail() {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.getString(R.string.task_was_not_updated))
            )
        }
    }

    private fun showErrorMessage(exception: Exception) {
        _state.update {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(
                    exception.message ?: stringProvider.getString(R.string.unknown_error)
                )
            )
        }
    }
}