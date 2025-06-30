package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.entity.Task
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet.DetailsUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime


fun DetailsUiState.toState(): TaskUiState {
    return TaskUiState(
        id = this.id,
        title = this.title,
        description = this.description,
        dueDate = this.dueDate,
        priority = this.priority,
        status = this.status,
        categoryId = this.categoryId,
        createdAt = this.createdAt
    )
}

fun DetailsUiState.toDomain(): Task {
    return Task(
        id = this.id,
        title = this.title,
        description = this.description,
        status = this.status.toDomain(),
        dueDate = LocalDate.parse(this.dueDate),
        priority = this.priority.toDomain(),
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
        description = this.description.orEmpty(),
        status = this.status.toState(),
        createdAt = this.createdAt.toString(),
        categoryImagePath = "",
        moveStatusToLabel = ""
    )
}

