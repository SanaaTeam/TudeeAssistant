package com.sanaa.tudee_assistant.presentation.bottom_sheet.add_edit_task

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryUiModel

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