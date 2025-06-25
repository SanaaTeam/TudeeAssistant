package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiState

interface HomeScreenInteractionsListener {
    fun onToggleColorTheme()
    fun snackBarSuccess(message: String)
    fun snackBarError(errorMessage: String)
    fun onHideSnackBar()
    fun onTaskClick(taskUiState: TaskUiState)
    fun onDismissTaskDetails()
}