package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState()) {

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
            taskService.getAllTasks().collect { tasks ->
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

    fun onAddTask() {
    }

    fun onChangeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.setDarkTheme(isDarkTheme)
        }
    }

    fun onAddTaskSuccess() {
        getTasks()
    }

    fun onAddTaskHasError(errorMessage: String) {

    }

    fun onTaskClick(categoryTaskState: TaskUiState) {

    }

    fun onOpenCategory(taskUiStatus: TaskUiStatus) {

    }
}