package com.sanaa.tudee_assistant.presentation.model

data class TaskUiModel(
    val id: Int?,
    val title: String,
    val description: String?,
    val dueDate: String?,
    val categoryImagePath: String,
    val priority: TaskUiPriority,
    val status: TaskUiStatus,
    val categoryId: Int,
)