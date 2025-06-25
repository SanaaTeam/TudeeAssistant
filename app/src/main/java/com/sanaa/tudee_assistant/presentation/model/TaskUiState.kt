package com.sanaa.tudee_assistant.presentation.model

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class TaskUiState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val dueDate: String = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val categoryId: Int = 0,
    val createdAt: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
)