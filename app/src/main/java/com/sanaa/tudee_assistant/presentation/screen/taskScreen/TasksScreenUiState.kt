package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import com.sanaa.tudee_assistant.presentation.model.TaskUiModel
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
