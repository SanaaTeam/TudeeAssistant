package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

data class CategoryScreenUiState(
    val currentDateCategory: List<CategoryUiState> = emptyList(),

    val isLoading: Boolean = false,
    val isAddCategorySheetVisible: Boolean = false,
    val isOperationSuccessful: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null,
    val currentCategory: CategoryUiState = CategoryUiState(),
    val categoryTitle: String = "",
    val selectedImageUri: Uri? = null,
    val isEditMode: Boolean = false,
    val navigateToCategoryId: Long? = null,
)

