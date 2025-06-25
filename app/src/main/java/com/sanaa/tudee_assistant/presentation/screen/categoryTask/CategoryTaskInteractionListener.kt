package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState

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
    fun onTaskClicked(task: TaskUiState)
    fun onTaskDetailsDismiss()
    fun onTaskEditClicked(task: TaskUiState)
    fun onTaskEditDismiss()
}