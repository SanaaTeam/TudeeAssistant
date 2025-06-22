package com.sanaa.tudee_assistant.domain.model

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val status: TaskStatus,
    val dueDate: LocalDate,
    val priority: TaskPriority,
    val categoryId: Int,
    val createdAt: LocalDateTime,
) {
    enum class TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }

    enum class TaskPriority {
        LOW,
        MEDIUM,
        HIGH
    }
}