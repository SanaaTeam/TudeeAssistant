package com.sanaa.tudee_assistant.presentation.screen.category

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState

data class CategoryScreenUiState(
    val allCategories: List<CategoryUiState> = emptyList(),
    val isLoading: Boolean = false,
    val isAddCategorySheetVisible: Boolean = false,
    val snackBarState: SnackBarState = SnackBarState(),
    val currentCategory: CategoryUiState = CategoryUiState(),
    val newCategory: CategoryUiState = CategoryUiState(),
    val isEditMode: Boolean = false,
)