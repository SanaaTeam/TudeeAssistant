package com.sanaa.tudee_assistant.presentation.state

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.LocalDateTime


data class TaskUiModel(
    val id: Int?,
    val title: String,
    val description: String?,
    val dueDate: String?,
    val categoryId: Int,
    val categoryImagePath: String,
    val priority: TaskUiPriority,
    val status: TaskUiStatus,
    val createdAt: LocalDateTime,
)