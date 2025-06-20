package com.sanaa.tudee_assistant.presentation.model

data class TaskUiState(
    val id: Int? = null,
    val title: String = "",
    val description: String? = null,
    val dueDate: String? = null,
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val categoryId: Int = 0,
)