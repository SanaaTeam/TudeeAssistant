package com.sanaa.tudee_assistant.domain.model

import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus
import kotlinx.datetime.LocalDate

class NewTask (
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val dueDate: LocalDate,
    val priority: TaskPriority,
    val categoryId: Int,
)