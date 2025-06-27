package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.datetime.LocalDate


data class TasksScreenUiState(
    val currentDateTasks: List<TaskUiState> = emptyList(),
    val snackBarState: SnackBarState = SnackBarState(),
    val showAddTaskBottomSheet: Boolean = false,
    val showEditTaskBottomSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val showDeleteTaskBottomSheet: Boolean = false,
    val selectedStatusTab: TaskUiStatus = TaskUiStatus.TODO,
    val selectedTask: TaskUiState? = null,
    val selectedDate: LocalDate = DateUtil.today.date,
    val categories: List<CategoryUiState> = emptyList(),
)
