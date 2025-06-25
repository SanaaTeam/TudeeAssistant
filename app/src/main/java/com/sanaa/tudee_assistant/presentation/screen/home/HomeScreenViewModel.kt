package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.isDarkTheme.collect { isDarkTheme ->
                _state.update { it.copy(isDarkTheme = isDarkTheme) }
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val today = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            taskService.getTasksByDueDate(today).collect { tasks ->
                _state.update { state ->
                    state.copy(
                        tasks = tasks.map { it.toState() }
                    )
                }

                categoryService.getCategories().collect { categories ->
                    _state.update { state ->
                        state.copy(
                            categories = categories.map { it.toState(tasks.size) }
                        )
                    }
                }
            }
        }
    }

    override fun onToggleColorTheme() {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.setDarkTheme(state.value.isDarkTheme.not())
        }
    }

    override fun snackBarSuccess(message: String) {
        getTasks()
        _state.update { it.copy(snackBarState = SnackBarState.getInstance(message)) }
    }

    override fun snackBarError(errorMessage: String) {
        _state.update { it.copy(snackBarState = SnackBarState.getErrorInstance(errorMessage)) }
    }

    override fun onHideSnackBar() {
        _state.update { it.copy(snackBarState = SnackBarState.hide()) }
    }

    override fun onTaskClick(taskUiState: TaskUiState) {
        _state.update { it.copy(selectedTask = taskUiState) }
    }

    override fun onDismissTaskDetails() {
        _state.update { it.copy(selectedTask = null) }
    }
}