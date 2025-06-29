package com.sanaa.tudee_assistant.domain.entity

import com.sanaa.tudee_assistant.domain.entity.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.entity.Task.TaskStatus
import kotlinx.datetime.LocalDate

data class TaskCreationRequest(
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val dueDate: LocalDate,
    val priority: TaskPriority,
    val categoryId: Int,
)