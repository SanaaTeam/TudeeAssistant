package com.sanaa.tudee_assistant.presentation.designSystem.component.snackBar

sealed class SnackBarState {
    data class Success(val message: String) : SnackBarState()
    data class Error(val message: String) : SnackBarState()
}