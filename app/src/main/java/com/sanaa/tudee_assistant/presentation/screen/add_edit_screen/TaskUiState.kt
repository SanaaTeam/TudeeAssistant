package com.sanaa.tudee_assistant.presentation.screen.add_edit_screen

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.model.CategoryState
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class AddTaskUiState(
    val taskUiModel: TaskUiModel = TaskUiModel(
        id = null,
        title = "",
        description = "",
        dueDate = null,
        categoryId = -1,

        categoryImagePath = "",
        priority = TaskUiPriority.LOW,
        status = TaskUiStatus.TODO,
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ),
    val categories: List<CategoryState> = emptyList(),
    val selectedCategory: Category? = null,
    val isButtonEnabled: Boolean = false,
    val isOperationSuccessful: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)