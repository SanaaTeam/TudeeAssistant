package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiState


fun Task.toState(): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        dueDate = dueDate?.toString() ?: "",
        categoryId = categoryId,
        priority = priority.toState(),
        description = description ?: "",
        status = status.toState(),
    )
}

fun Task.TaskPriority.toState(): TaskUiPriority {
    return when (this) {
        Task.TaskPriority.LOW -> TaskUiPriority.LOW
        Task.TaskPriority.MEDIUM -> TaskUiPriority.MEDIUM
        Task.TaskPriority.HIGH -> TaskUiPriority.HIGH
    }
}
fun Task.TaskStatus.toState(): TaskUiStatus {
    return when (this) {
        Task.TaskStatus.TODO -> TaskUiStatus.TODO
        Task.TaskStatus.IN_PROGRESS -> TaskUiStatus.IN_PROGRESS
        Task.TaskStatus.DONE -> TaskUiStatus.DONE
    }
}