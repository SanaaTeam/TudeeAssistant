package com.sanaa.tudee_assistant.presentation.screen.add_edit_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toState
import com.sanaa.tudee_assistant.presentation.state.mapper.toTask
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.state.mapper.toNewTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TaskFormViewModel(
    private val taskService: TaskService,
    private val categoryService: CategoryService,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    init{
        viewModelScope.launch {
            categoryService.getCategories().collect { categoryList ->
                _uiState.update {
                    it.copy(
                        categories = categoryList.map { category -> category.toState(0) }
                    )
                }
            }
        }
    }

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

    fun onCategorySelected(category: CategoryUiState) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                taskUiState = it.taskUiState.copy(categoryId = category.id)
            )
        }
        validateInputs()
    }

    fun addTask() {
        if (!uiState.value.isButtonEnabled) return
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val newTask = uiState.value.taskUiState.copy(
                    status = TaskUiStatus.TODO
                ).toNewTask()
                taskService.addTask(newTask)
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
                val task = uiState.value.taskUiState.toTask()
                taskService.updateTask(task)
                _uiState.update { it.copy(isOperationSuccessful = true, isLoading = false) }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun resetState() {
        _uiState.update {
            AddTaskUiState(categories = it.categories)
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
