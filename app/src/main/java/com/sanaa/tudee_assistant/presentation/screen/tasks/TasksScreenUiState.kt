package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


data class TasksScreenUiState(
    val currentDateTasks: List<TaskUiState> = emptyList(),
    val errorMessageStringId: Int? = null,
    val successMessageStringId: Int? = null,
    val showAddTaskBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val showDeleteTaskBottomSheet: Boolean = false,
    val selectedStatusTab: TaskUiStatus = TaskUiStatus.TODO,
    val selectedTask: TaskUiState? = null,
    val selectedDate: LocalDate = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
    val categories: List<CategoryUiState> = emptyList(),
)
