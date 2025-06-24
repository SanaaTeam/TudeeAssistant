package com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask

import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class AddTaskUiState(
    val taskUiState: TaskUiState = TaskUiState(
        id = 0,
        title = "",
        description = "",
        dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
        categoryId = -1,
        priority = TaskUiPriority.LOW,
        status = TaskUiStatus.TODO,
    ),
    val categories: List<CategoryUiState> = emptyList(),
    val selectedCategory: CategoryUiState? = null,
    val isButtonEnabled: Boolean = false,
    val isOperationSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)