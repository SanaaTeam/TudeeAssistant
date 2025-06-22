package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

data class CategoryTaskScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val currentCategory: CategoryUiState = CategoryUiState(
        id = -1,
        name = "",
        imagePath = "",
        isDefault = false,
        tasksCount = 0
    ),
    val showDeleteCategoryBottomSheet: Boolean = false,
    val showUpdateCategoryBottomSheet: Boolean = false,
    val currentSelectedTaskStatus: TaskUiStatus = TaskUiStatus.TODO,
    val tasks: List<TaskUiState> = emptyList()
)