package com.sanaa.tudee_assistant.presentation.model

import com.sanaa.tudee_assistant.presentation.utils.DateUtil

data class TaskUiState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val dueDate: String = DateUtil.today.date.toString(),
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val categoryId: Int = 0,
    val createdAt: String = DateUtil.today.toString(),
)