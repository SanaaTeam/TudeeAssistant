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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState()), HomeScreenActionsListener {

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
            taskService.getAllTasks().collect { tasks ->
                val todayTasks = tasks.filter { it.dueDate == today }
                _state.update { state ->
                    state.copy(
                        tasks = todayTasks.map { it.toState() }
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

    override fun onAddTask() {
    }

    override fun onChangeTheme(isDarkTheme: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferencesManager.setDarkTheme(isDarkTheme)
        }
    }

    override fun onAddTaskSuccess() {
        getTasks()
    }

    override fun onAddTaskHasError(errorMessage: String) {

    }

    override fun onTaskClick(categoryTaskState: TaskUiState) {

    }

    override fun onOpenCategory(taskUiStatus: TaskUiStatus) {

    }
}