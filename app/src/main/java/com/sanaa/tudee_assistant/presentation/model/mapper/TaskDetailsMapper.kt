package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.DetailsUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


fun DetailsUiState.toTaskUiState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate,
        priority = this.priority,
        status = this.status,
        categoryId = this.id,
        createdAt = this.createdAt
    )
}

fun DetailsUiState.toTask(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        status = this.status.toTaskStatus(),
        dueDate = LocalDate.parse(this.dueDate),
        priority = this.priority.toTaskPriority(),
        categoryId = this.categoryId,
        createdAt = LocalDateTime.parse(this.createdAt),
    )
}

fun Task.toDetailsState(): DetailsUiState {
    return DetailsUiState(
        id = this.id,
        title = this.title,
        dueDate = this.dueDate.toString(),
        categoryId = this.categoryId,
        priority = this.priority.toState(),
        description = this.description ?: "",
        status = this.status.toState(),
        createdAt = this.createdAt.toString(),
        categoryImagePath = "",
        moveStatusToLabel = ""
    )
}

