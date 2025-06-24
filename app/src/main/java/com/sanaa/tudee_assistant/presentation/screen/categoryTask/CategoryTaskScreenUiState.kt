package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

data class CategoryTaskScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val currentCategory: CategoryUiState = CategoryUiState(),
    val showDeleteCategoryBottomSheet: Boolean = false,
    val showEditCategoryBottomSheet: Boolean = false,
    val currentSelectedTaskStatus: TaskUiStatus = TaskUiStatus.TODO,
    val selectedTapIndex: Int = 1,
    val allCategoryTasks: List<TaskUiState> = emptyList(),
    val filteredTasks: List<TaskUiState> = emptyList(),
    val editCategory: CategoryUiState = CategoryUiState(),

)