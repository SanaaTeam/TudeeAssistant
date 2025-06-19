package com.sanaa.tudee_assistant.presentation.screen.taskScreen

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
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE
        ),
        TaskUiModel(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO
        ),
        TaskUiModel(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        ),
    )
}