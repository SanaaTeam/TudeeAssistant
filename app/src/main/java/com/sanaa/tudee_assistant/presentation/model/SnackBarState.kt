package com.sanaa.tudee_assistant.presentation.model

data class SnackBarState(
    val isVisible: Boolean = false,
    val message: String = "",
    val snackBarStatus: SnackBarStatus = SnackBarStatus.SUCCESS,
) {
    companion object {
        fun hide(): SnackBarState {
            return SnackBarState(
                snackBarStatus = SnackBarStatus.SUCCESS,
                message = "",
                isVisible = false
            )
        }

        fun getInstance(message: String): SnackBarState {
            return SnackBarState(
                snackBarStatus = SnackBarStatus.SUCCESS,
                message = message,
                isVisible = true
            )
        }

        fun getErrorInstance(message: String): SnackBarState {
            return SnackBarState(
                snackBarStatus = SnackBarStatus.ERROR,
                message = message,
                isVisible = true
            )
        }
    }
}