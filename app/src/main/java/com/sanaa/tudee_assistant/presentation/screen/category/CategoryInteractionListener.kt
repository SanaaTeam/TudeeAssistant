package com.sanaa.tudee_assistant.presentation.screen.category

import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

interface CategoryInteractionListener {
    fun onAddCategory(newCategory: CategoryUiState)
    fun onToggleAddCategorySheet(isVisible: Boolean)
    fun onSnackBarShown()
}