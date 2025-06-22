package com.sanaa.tudee_assistant.presentation.screen.categoryTask

interface CategoryTaskInteractionListener {
    fun onDeleteClicked()
    fun onEditClicked()
    fun onEditDismissClicked()
    fun onConfirmDeleteClicked()
    fun onDeleteDismiss()
    fun onStatusChanged(index: Int)
}