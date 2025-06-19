package com.sanaa.tudee_assistant.presentation.screen.taskScreen.mapper

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
import kotlinx.datetime.LocalDate


fun Task.toUiModel(
    categoryImagePath: String
): TaskUiModel {
    return TaskUiModel(
        id = id,
        title = title,
        dueDate = dueDate?.toString() ?: "",
        categoryImagePath = categoryImagePath,
        priority = priority.toUiModel(),
        description = description ?: "",
        status = status.toUiModel(),
        categoryId = categoryId,
        createdAt = createdAt,
    )
}

fun TaskPriority.toUiModel(): TaskUiPriority {
    return when (this) {
        TaskPriority.LOW -> TaskUiPriority.LOW
        TaskPriority.MEDIUM -> TaskUiPriority.MEDIUM
        TaskPriority.HIGH -> TaskUiPriority.HIGH
    }
}

fun Task.TaskStatus.toUiModel(): TaskUiStatus {
    return when (this) {
        Task.TaskStatus.TODO -> TaskUiStatus.TODO
        Task.TaskStatus.IN_PROGRESS -> TaskUiStatus.IN_PROGRESS
        Task.TaskStatus.DONE -> TaskUiStatus.DONE
    }
}


fun TaskUiPriority.toTaskPriority(): TaskPriority {
    return when (this) {
        TaskUiPriority.LOW -> TaskPriority.LOW
        TaskUiPriority.MEDIUM -> TaskPriority.MEDIUM
        TaskUiPriority.HIGH -> TaskPriority.HIGH
    }
}

fun TaskUiModel.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        status = status.toTaskStatus(),
        dueDate = dueDate?.let { LocalDate.parse(it) },
        priority = priority.toTaskPriority(),
        categoryId = categoryId,
        createdAt = createdAt
    )
}

fun TaskUiStatus.toTaskStatus(): Task.TaskStatus {
    return when (this) {
        TaskUiStatus.TODO -> Task.TaskStatus.TODO
        TaskUiStatus.IN_PROGRESS -> Task.TaskStatus.IN_PROGRESS
        TaskUiStatus.DONE -> Task.TaskStatus.DONE
    }
}