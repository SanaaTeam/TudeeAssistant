package com.sanaa.tudee_assistant.presentation.screen.main

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

data class MainUiState(
    val lastSelectedTaskStatus: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
)