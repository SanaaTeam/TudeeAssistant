package com.sanaa.tudee_assistant.presentation.screen.add_edit_screen

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screens.category.CategoryUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

data class AddTaskUiState(
    val taskUiState: TaskUiState = TaskUiState(
        id = null,
        title = "",
        description = "",
        dueDate = null,
        categoryId = -1,
        priority = TaskUiPriority.LOW,
        status = TaskUiStatus.TODO,
    ),
    val categories: List<CategoryUiModel> = emptyList(),
    val selectedCategory: CategoryUiModel? = null,
    val isButtonEnabled: Boolean = false,
    val isOperationSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)