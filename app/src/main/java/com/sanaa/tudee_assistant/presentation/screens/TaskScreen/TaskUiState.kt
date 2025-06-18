package com.sanaa.tudee_assistant.presentation.screens.TaskScreen

import androidx.compose.ui.graphics.painter.Painter
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class TasksScreenUiState(

    val tasks: List<TaskUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val showEditDialog: Boolean = false,
    val showDeleteDialog: Boolean = false,
    val selectedTask: TaskUiModel? = null,
    val showAddTaskDialog: Boolean = false,
    val selectedTaskStatus: TaskStatus = TaskStatus.TODO,
    val showViewTaskDialog: Boolean = false,
    val selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val newTask: TaskUiModel = TaskUiModel(),
)

data class TaskUiModel(
    val id: Int = 0,
    val title: String = "",
    val dueDate: String = "",
    val categoryImage: Painter = TODO(),
    val categoryId : Int = 0,
    val priority: TaskPriority = TaskPriority.LOW,
    val description: String = "",
)

data class CategoryUiModel(
    val id: Int,
    val name: String,
    val cateGoryImage: Painter,
    val categoryColor: Int,
    val isSelected: Boolean = false,
)


fun Task.toUiModel(): TaskUiModel {
    return TaskUiModel(
        id = id,
        title = title,
        dueDate = dueDate?.toString() ?: "",
        categoryImage = TODO(),
        priority = priority.toUiModel(),
        description = description ?: "",
    )
}

fun TaskUiModel.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        status = Task.TaskStatus.TODO,
        dueDate = LocalDate.parse(dueDate),
        priority = priority.toDomain(),
        categoryId = TODO(),
        createdAt = TODO(),
    )

}


fun TaskStatus.toDomain(): Task.TaskStatus {
    return when (this) {
        TaskStatus.TODO -> Task.TaskStatus.TODO
        TaskStatus.IN_PROGRESS -> Task.TaskStatus.IN_PROGRESS
        TaskStatus.DONE -> Task.TaskStatus.DONE
    }
}

fun Task.TaskPriority.toUiModel(): TaskPriority {
    return when (this) {
        Task.TaskPriority.LOW -> TaskPriority.LOW
        Task.TaskPriority.MEDIUM -> TaskPriority.MEDIUM
        Task.TaskPriority.HIGH -> TaskPriority.HIGH
    }
}

fun TaskPriority.toDomain(): Task.TaskPriority {
    return when (this) {
        TaskPriority.LOW -> Task.TaskPriority.LOW
        TaskPriority.MEDIUM -> Task.TaskPriority.MEDIUM
        TaskPriority.HIGH -> Task.TaskPriority.HIGH
    }
}

