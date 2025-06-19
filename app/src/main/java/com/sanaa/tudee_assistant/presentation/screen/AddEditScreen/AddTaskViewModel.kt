package com.sanaa.tudee_assistant.presentation.screen.AddEditScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddTaskException
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AddTaskViewModel(
    private val taskService: TaskService,
    val categoryService: CategoryService
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(title = title) }
        validateInputs()
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(selectedDate = date) }
        validateInputs()
    }

    fun onPrioritySelected(priority: Task.TaskPriority) {
        _uiState.update { it.copy(selectedPriority = priority) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
        validateInputs()
    }

    fun addTask() {
        if (!uiState.value.isAddTaskButtonEnabled) return

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                val currentState = _uiState.value
                val task = Task(
                    id = null,
                    title = currentState.title,
                    description = currentState.description.takeIf { it.isNotBlank() },
                    status = Task.TaskStatus.TODO,
                    dueDate = currentState.selectedDate,
                    priority = currentState.selectedPriority ?: Task.TaskPriority.LOW,
                    categoryId = currentState.selectedCategory?.id ?: throw FailedToAddTaskException(),
                    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
                taskService.addTask(task)
                _uiState.update { it.copy(taskAddedSuccessfully = true, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to add task"
                    )
                }
            }
        }
    }

    private fun validateInputs() {
        val currentState = _uiState.value
        val isTitleValid = currentState.title.isNotBlank()
        val isDateValid = currentState.selectedDate != null
        val isCategorySelected = currentState.selectedCategory != null
        _uiState.update {
            it.copy(
                isAddTaskButtonEnabled = isTitleValid && isDateValid && isCategorySelected
            )
        }
    }
}
