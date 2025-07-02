package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.datetime.LocalDateTime

data class HomeScreenUiState(
    val isDarkTheme: Boolean = false,
    val todayDate: LocalDateTime = DateUtil.today,
    val taskCounts: List<Pair<Int, TaskUiStatus>> = emptyList(),
    val tasks: List<TaskUiState> = emptyList(),
    val categories: List<CategoryUiState> = emptyList(),
    val selectedTask: TaskUiState? = null,
    val snackBarState: SnackBarState = SnackBarState.hide(),
    val showAddTaskSheet: Boolean = false,
    val showEditTaskSheet: Boolean = false,
    val showTaskDetailsBottomSheet: Boolean = false,
    val taskToEdit: TaskUiState? = null,
    val sliderUiState: SliderUiState = SliderUiState()
)