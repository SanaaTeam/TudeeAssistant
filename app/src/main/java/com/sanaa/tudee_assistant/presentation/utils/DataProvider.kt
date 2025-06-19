package com.sanaa.tudee_assistant.presentation.utils

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DataProvider {

    fun getTasksSample() = listOf(
        TaskUiModel(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 1,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 2,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 3,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
        TaskUiModel(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 4,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
        TaskUiModel(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 5,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
        TaskUiModel(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryId = 6,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.Companion.UTC)
        ),
    )
}