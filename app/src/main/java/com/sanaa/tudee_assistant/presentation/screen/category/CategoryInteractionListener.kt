package com.sanaa.tudee_assistant.presentation.screen.category

import android.net.Uri
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState

interface CategoryInteractionListener {
    fun onAddCategory(newCategory: CategoryUiState)
    fun onToggleAddCategorySheet(isVisible: Boolean)
    fun onCategoryTitleChange(title: String)
    fun onCategoryImageSelected(uri: Uri?)
    fun isFormValid(): Boolean
    fun onHideSnakeBar()
}