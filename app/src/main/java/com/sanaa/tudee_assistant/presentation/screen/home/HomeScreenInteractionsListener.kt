package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiState

interface HomeScreenInteractionsListener {
    // Theme and Snack-bar
    fun onToggleColorTheme()
    fun onHideSnackBar()

    // Task Details Sheet
    fun onTaskClick(taskUiState: TaskUiState)
    fun onDismissTaskDetails()

    // Add Task Sheet
    fun onShowAddTaskSheet()
    fun onHideAddTaskSheet()
    fun onAddTaskSuccess()
    fun onAddTaskError(errorMessage: String)

    // Edit Task Sheet
    fun onShowEditTaskSheet(taskToEdit: TaskUiState)
    fun onHideEditTaskSheet()
    fun onEditTaskSuccess()
    fun onEditTaskError(errorMessage: String)

    // Status Update from Details
    fun onMoveStatusSuccess()
    fun onMoveStatusFail()
}