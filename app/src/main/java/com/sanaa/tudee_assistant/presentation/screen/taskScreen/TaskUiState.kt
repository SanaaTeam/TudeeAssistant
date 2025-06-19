package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class TasksScreenUiState(
    val currentDateTasks: List<TaskUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val showAddTaskDialog: Boolean = false,
    val showEditDialog: Boolean = false,
    val showTaskDetailsDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val selectedTaskUiStatus: TaskUiStatus = TaskUiStatus.TODO,
    val selectedTask: TaskUiModel? = null,
    val selectedDate: LocalDate =
        Clock.System.now()
            .toLocalDateTime(TimeZone.UTC)
            .date,
)

data class TaskUiModel(
    val id: Int?,
    val title: String,
    val description: String?,
    val dueDate: String?,
    val categoryImagePath: String,
    val priority: TaskUiPriority,
    val status: TaskUiStatus,
)

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

