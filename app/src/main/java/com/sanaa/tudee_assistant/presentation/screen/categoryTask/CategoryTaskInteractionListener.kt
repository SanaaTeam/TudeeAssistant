package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import android.net.Uri

interface CategoryTaskInteractionListener {
    fun onDeleteClicked()
    fun onEditClicked()
    fun onEditDismissClicked()
    fun onConfirmDeleteClicked()
    fun onDeleteDismiss()
    fun onStatusChanged(index: Int)
    fun onImageSelect(image:Uri?)
    fun onTitleChange(title:String)
}