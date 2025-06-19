package com.sanaa.tudee_assistant.presentation.utils

import com.sanaa.tudee_assistant.presentation.model.TaskUiModel
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

object DataProvider {

    fun getTasksSample() = listOf(
        TaskUiModel(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 1
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE,
            categoryId = 2
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE,
            categoryId = 3
        ),
        TaskUiModel(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO,
            categoryId = 4
        ),
        TaskUiModel(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 5
        ),
        TaskUiModel(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO,
            categoryId = 6
        ),
    )
}