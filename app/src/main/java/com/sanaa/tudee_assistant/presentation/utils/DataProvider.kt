package com.sanaa.tudee_assistant.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

object DataProvider {

    fun getTasksSample() = listOf(
        TaskUiState(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = DateUtil.today.date.toString(),
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 1
        ),
        TaskUiState(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = "2025-05-12",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE,
            categoryId = 2
        ),
        TaskUiState(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = DateUtil.today.date.toString(),
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE,
            categoryId = 3
        ),
        TaskUiState(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = DateUtil.today.date.toString(),
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO,
            categoryId = 4
        ),
        TaskUiState(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = DateUtil.today.date.toString(),
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 5,
        ),
        TaskUiState(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = DateUtil.today.date.toString(),
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO,
            categoryId = 6
        ),
    )

    @SuppressLint("DiscouragedApi")
    fun getStringResourceByName(resourceName: String, context: Context): String {
        val allowedResourceName = resourceName.filter { it.isLetterOrDigit() || it == '_' }
        val resourceId =
            context.resources.getIdentifier(allowedResourceName, "string", context.packageName)

        return try {
            if (resourceId != 0) {
                context.resources.getResourceTypeName(resourceId).let { type ->
                    if (type == "string") {
                        context.getString(resourceId)
                    } else {
                        resourceName
                    }
                }
            } else {
                // Resource not found
                resourceName
            }
        } catch (e: Resources.NotFoundException) {
            resourceName
        }
    }
}