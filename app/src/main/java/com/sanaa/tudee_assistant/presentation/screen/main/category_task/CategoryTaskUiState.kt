package com.sanaa.tudee_assistant.presentation.screen.main.category_task

import com.sanaa.tudee_assistant.presentation.model.CategoryTaskState

data class CategoryTaskUiState(
    val categoryName: String = "",
    val tasks: List<CategoryTaskState> = emptyList(),
)