package com.sanaa.tudee_assistant.presentation.screen.add_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper.toTask
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TaskFormViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    fun loadTask(task: TaskUiState) {
        viewModelScope.launch(Dispatchers.IO) {
            taskService.getTaskCountByCategoryId(task.categoryId).collect { taskCount ->
                val category = categoryService
                    .getCategoryById(task.categoryId).toState(taskCount)
                _uiState.update {
                    it.copy(taskUiState = task, selectedCategory = category)
                }

                categoryService.getCategories().collect { categories ->
                    _uiState.update { state ->
                        state.copy(categories = categories.map { it.toState(taskCount) })
                    }
                }
            }
        }
    }

    fun loadCategoriesForNewTask() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryService.getCategories().collect { categories ->
                _uiState.update { state ->
                    state.copy(categories = categories.map { it.toState(0) })
                }
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(title = title))
        }
        validateInputs()
    }

    fun onDescriptionChange(description: String) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(description = description))
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(dueDate = date.toString()))
        }
        validateInputs()
    }

    fun onPrioritySelected(priority: TaskUiPriority) {
        _uiState.update {
            it.copy(taskUiState = it.taskUiState.copy(priority = priority))
        }
    }

    fun onCategorySelected(category: CategoryUiModel) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                taskUiState = it.taskUiState.copy(categoryId = category.id ?: -1)
            )
        }
        validateInputs()
    }

    fun addTask() {
        if (!uiState.value.isButtonEnabled) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val task = uiState.value.taskUiState.copy(
                    status = TaskUiStatus.TODO
                ).toTask(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()))
                taskService.addTask(task)
                _uiState.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun updateTask() {
        if (!uiState.value.isButtonEnabled) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val task = uiState.value.taskUiState.toTask(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
                taskService.updateTask(task)
                _uiState.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun resetState() {
        _uiState.update {
            AddTaskUiState()
        }
    }

    private fun handleError(e: Exception) {
        _uiState.update {
            it.copy(isLoading = false, error = e.message ?: "Failed to process task")
        }
    }

    private fun validateInputs() {
        val state = _uiState.value
        _uiState.update {
            it.copy(
                isButtonEnabled = state.taskUiState.title.isNotBlank() &&
                        state.selectedCategory != null
            )
        }
    }
}
