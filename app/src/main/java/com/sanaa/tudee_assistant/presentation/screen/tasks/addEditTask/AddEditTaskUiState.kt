package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus


data class AddTaskUiState(
    val taskUiState: TaskUiState = TaskUiState(
        id = 0,
        title = "",
        description = "",
        dueDate = "",
        categoryId = -1,
        priority = TaskUiPriority.LOW,
        status = TaskUiStatus.TODO,
    ),
    val categories: List<CategoryUiState> = emptyList(),
    val selectedCategory: CategoryUiState? = null,
    val isButtonEnabled: Boolean = false,
    val isOperationSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)