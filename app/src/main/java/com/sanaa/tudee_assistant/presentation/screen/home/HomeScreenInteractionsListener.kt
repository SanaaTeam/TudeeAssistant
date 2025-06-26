package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiState

interface HomeScreenInteractionsListener {
    fun onToggleColorTheme()
    fun onHideSnackBar()

    fun onTaskClick(taskUiState: TaskUiState)
    fun onShowTaskDetails(show: Boolean)

    fun onShowAddTaskSheet()
    fun onHideAddTaskSheet()
    fun onAddTaskSuccess()
    fun onAddTaskError(errorMessage: String)

    fun onShowEditTaskSheet(taskToEdit: TaskUiState)
    fun onHideEditTaskSheet()
    fun onEditTaskSuccess()
    fun onEditTaskError(errorMessage: String)

    fun onMoveStatusSuccess()
    fun onMoveStatusFail()
}