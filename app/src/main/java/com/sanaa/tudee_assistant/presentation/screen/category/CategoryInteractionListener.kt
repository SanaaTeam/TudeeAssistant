package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

interface CategoryInteractionListener {
    fun onAddCategory(newCategory: CategoryUiState)
    fun onToggleAddCategorySheet(isVisible: Boolean)
    fun onSnackBarShown()
    fun onCategoryTitleChange(title: String)
    fun onCategoryImageSelected(uri: Uri?)
    fun isFormValid(): Boolean
}