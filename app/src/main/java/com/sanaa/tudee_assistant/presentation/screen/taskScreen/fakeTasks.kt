package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


val fakeTasks = listOf(
    Task(
        id = 1,
        title = "Organize Study Desk",
        description = "Review cell structure and functions for tomorrow...",
        dueDate = LocalDate(2025, 6, 22),
        categoryId = 1,
        priority = Task.TaskPriority.MEDIUM,
        status = Task.TaskStatus.IN_PROGRESS,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 2,
        title = "Priority Task",
        description = "Finish Kotlin Compose project",
        dueDate = LocalDate(2025, 6, 20),
        categoryId = 1,
        priority = Task.TaskPriority.HIGH,
        status = Task.TaskStatus.TODO,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    Task(
        id = 3,
        title = "Hello",
        description = "Buy milk and bread",
        dueDate = LocalDate(2025, 6, 25),
        categoryId = 1,
        priority = Task.TaskPriority.LOW,
        status = Task.TaskStatus.DONE,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    )
)