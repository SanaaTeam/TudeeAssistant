package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

interface CategoryTaskInteractionListener {
    fun onDeleteClicked()
    fun onEditClicked()
    fun onEditDismissClicked()
    fun onConfirmDeleteClicked()
    fun onDeleteDismiss()
    fun onStatusChanged(index: Int)
    fun onImageSelect(image:Uri?)
    fun onTitleChange(title:String)
    fun onSaveEditClicked(category: CategoryUiState)
}