package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.entity.Task
import com.sanaa.tudee_assistant.domain.entity.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.entity.Task.TaskStatus
import com.sanaa.tudee_assistant.domain.entity.TaskCreationRequest
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


fun Task.toState(): TaskUiState {
    return TaskUiState(
        id = id,
        title = title,
        dueDate = dueDate.toString(),
        categoryId = categoryId,
        priority = priority.toState(),
        description = description.orEmpty(),
        status = status.toState(),
        createdAt = createdAt.toString(),
    )
}

fun List<Task>.toState(): List<TaskUiState> {
    return this.map { task -> task.toState() }
}

fun TaskPriority.toState(): TaskUiPriority {
    return when (this) {
        TaskPriority.LOW -> TaskUiPriority.LOW
        TaskPriority.MEDIUM -> TaskUiPriority.MEDIUM
        TaskPriority.HIGH -> TaskUiPriority.HIGH
    }
}

fun TaskStatus.toState(): TaskUiStatus {
    return when (this) {
        TaskStatus.TODO -> TaskUiStatus.TODO
        TaskStatus.IN_PROGRESS -> TaskUiStatus.IN_PROGRESS
        TaskStatus.DONE -> TaskUiStatus.DONE
    }
}

fun TaskUiState.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        status = status.toDomain(),
        dueDate = LocalDate.parse(dueDate),
        priority = priority.toDomain(),
        categoryId = categoryId,
        createdAt = LocalDateTime.parse(createdAt),
    )
}

fun TaskUiPriority.toDomain(): TaskPriority {
    return when (this) {
        TaskUiPriority.LOW -> TaskPriority.LOW
        TaskUiPriority.MEDIUM -> TaskPriority.MEDIUM
        TaskUiPriority.HIGH -> TaskPriority.HIGH
    }
}

fun TaskUiStatus.toDomain(): TaskStatus {
    return when (this) {
        TaskUiStatus.TODO -> TaskStatus.TODO
        TaskUiStatus.IN_PROGRESS -> TaskStatus.IN_PROGRESS
        TaskUiStatus.DONE -> TaskStatus.DONE
    }
}

fun TaskUiState.toCreationRequest(): TaskCreationRequest {
    return TaskCreationRequest(
        title = title,
        description = description,
        status = status.toDomain(),
        dueDate = LocalDate.parse(dueDate),
        priority = priority.toDomain(),
        categoryId = categoryId,
    )
}