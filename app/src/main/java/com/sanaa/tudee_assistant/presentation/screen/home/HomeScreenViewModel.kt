package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState()) {

    init {
        getCategories()
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getAllTasks().collect { tasks ->
                _state.update { state ->
                    state.copy(
                        tasks = tasks.map { it.toState() }
                    )
                }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            categoryService.getCategories().collect { categories ->
                _state.update { state ->
                    state.copy(
                        categories = categories.map { it.toState() }
                    )
                }
            }
        }
    }

    fun onAddTask() {

    }

    fun onTaskClick(categoryTaskState: TaskUiState) {

    }

    fun onOpenCategory(taskUiStatus: TaskUiStatus) {

    }
}