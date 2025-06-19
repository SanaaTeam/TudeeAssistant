package com.sanaa.tudee_assistant.presentation.utils

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

object DataProvider {

    fun getTasksSample() = listOf(
        TaskUiState(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 1
        ),
        TaskUiState(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = "2025-05-12",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE,
            categoryId = 2
        ),
        TaskUiState(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE,
            categoryId = 3
        ),
        TaskUiState(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO,
            categoryId = 4
        ),
        TaskUiState(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 5
        ),
        TaskUiState(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO,
            categoryId = 6
        ),
    )
}