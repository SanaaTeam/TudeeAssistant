package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

data class CategoryTaskScreenUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val currentCategory: CategoryUiState = CategoryUiState(),
    val showDeleteCategoryBottomSheet: Boolean = false,
    val navigateBackToCategoryList: Boolean = false,
    val showEditCategoryBottomSheet: Boolean = false,
    val currentSelectedTaskStatus: TaskUiStatus = TaskUiStatus.TODO,
    val selectedTapIndex: Int = 1,
    val allCategoryTasks: List<TaskUiState> = emptyList(),
    val filteredTasks: List<TaskUiState> = emptyList(),
    val editCategory: CategoryUiState = CategoryUiState(),
    val selectedTask: TaskUiState? = null,
    val showTaskDetailsBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val snackBarState: SnackBarState = SnackBarState(),
)