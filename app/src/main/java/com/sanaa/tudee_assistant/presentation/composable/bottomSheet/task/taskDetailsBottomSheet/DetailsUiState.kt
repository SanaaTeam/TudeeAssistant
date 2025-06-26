package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import androidx.compose.ui.res.stringResource
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.model.mapper.toTaskPriority
import com.sanaa.tudee_assistant.presentation.model.mapper.toTaskStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class DetailsUiState(
    val id: Int = 0,
    val title: String = "",
    val description: String? = null,
    val dueDate: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
    val priority: TaskUiPriority = TaskUiPriority.LOW,
    val status: TaskUiStatus = TaskUiStatus.IN_PROGRESS,
    val moveStatusToLabel: String = "",
    val categoryId: Int = 0,
    val categoryImagePath: String = "",
    val createdAt: String = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
)


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

