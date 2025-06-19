package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiModel
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus


fun Task.toUiModel(
    categoryImagePath: String
): TaskUiModel {
    return TaskUiModel(
        id = id,
        title = title,
        dueDate = dueDate?.toString() ?: "",
        categoryImagePath = categoryImagePath,
        categoryId = categoryId,
        priority = priority.toUiModel(),
        description = description ?: "",
        status = status.toUiModel(),
    )
}

fun Task.TaskPriority.toUiModel(): TaskUiPriority {
    return when (this) {
        Task.TaskPriority.LOW -> TaskUiPriority.LOW
        Task.TaskPriority.MEDIUM -> TaskUiPriority.MEDIUM
        Task.TaskPriority.HIGH -> TaskUiPriority.HIGH
    }
}
fun Task.TaskStatus.toUiModel(): TaskUiStatus {
    return when (this) {
        Task.TaskStatus.TODO -> TaskUiStatus.TODO
        Task.TaskStatus.IN_PROGRESS -> TaskUiStatus.IN_PROGRESS
        Task.TaskStatus.DONE -> TaskUiStatus.DONE
    }
}