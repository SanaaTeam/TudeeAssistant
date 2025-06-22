package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

interface HomeScreenActionsListener {
    fun onAddTask()
    fun onChangeTheme(isDarkTheme: Boolean)
    fun onAddTaskSuccess()
    fun onAddTaskHasError(errorMessage: String)
    fun onTaskClick(categoryTaskState: TaskUiState)
    fun onOpenCategory(taskUiStatus: TaskUiStatus)
}