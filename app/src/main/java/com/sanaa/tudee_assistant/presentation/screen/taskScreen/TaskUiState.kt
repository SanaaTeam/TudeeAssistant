package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskStatus
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
    val selectedTaskStatus: TaskStatus = TaskStatus.TODO,
    val selectedTask: TaskUiModel? = null,
    val selectedDate: LocalDate =
        Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date,
)

data class TaskUiModel(
    val id: Int?,
    val title: String,
    val dueDate: String,
    val categoryImagePath: String,
    val priority: TaskPriority,
    val description: String,
)

fun Task.toUiModel(
   path: String
): TaskUiModel {
    return TaskUiModel(
        id = id,
        title = title,
        dueDate = dueDate?.toString() ?: "",
        categoryImagePath = path,
        priority = priority.toUiModel(),
        description = description ?: "",
    )
}

fun Task.TaskPriority.toUiModel(): TaskPriority {
    return when (this) {
        Task.TaskPriority.LOW -> TaskPriority.LOW
        Task.TaskPriority.MEDIUM -> TaskPriority.MEDIUM
        Task.TaskPriority.HIGH -> TaskPriority.HIGH
    }
}

