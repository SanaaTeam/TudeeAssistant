package com.sanaa.tudee_assistant.presentation.screen.AddEditScreen

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class AddTaskUiState(
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