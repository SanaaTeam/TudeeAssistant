package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiState

interface HomeScreenInteractionsListener {
    fun onToggleColorTheme()
    fun onAddTaskSuccess()
    fun onAddTaskError(errorMessage: String)
    fun onEditTaskSuccess()
    fun onEditTaskError(errorMessage: String)
    fun onHideSnackBar()
    fun onTaskClick(taskUiState: TaskUiState)
    fun onDismissTaskDetails()
    fun onMoveStatusSuccess()
    fun onMoveStatusFail()
}