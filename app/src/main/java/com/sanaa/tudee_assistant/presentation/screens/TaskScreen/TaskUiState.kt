package com.sanaa.tudee_assistant.presentation.screens.TaskScreen

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.datetime.LocalDate

data class TaskUiState(
    val title: String = "",
    val description: String = "",
    val selectedDate: LocalDate? = null,
    val selectedPriority: Task.TaskPriority? = null,
    val selectedCategory: Category? = null,
    val isAddTaskButtonEnabled: Boolean = false,
    val taskAddedSuccessfully: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)