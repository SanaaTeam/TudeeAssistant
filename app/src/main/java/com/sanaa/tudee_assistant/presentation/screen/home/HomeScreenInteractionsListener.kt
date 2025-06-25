package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiState

interface HomeScreenInteractionsListener {
    fun onToggleColorTheme()
    fun onAddTaskSuccess()
    fun onAddTaskHasError(errorMessage: String)
    fun onHideSnackBar()
    fun onTaskClick(taskUiState: TaskUiState)
    fun onDismissTaskDetails()
}