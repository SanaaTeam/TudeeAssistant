package com.sanaa.tudee_assistant.presentation.screens.category

import com.sanaa.tudee_assistant.domain.model.Category

data class CategoryUiState(
    val name: String = "",
    val imageUrl: String = "",
    val isDefault: Boolean = true,
    val tasksCount: Int = 0,
)

fun Category.toUiState(taskCount: Int): CategoryUiState {
    return CategoryUiState(
        name = this.name,
        imageUrl = this.imageUrl,
        isDefault = this.isDefault,
        tasksCount = taskCount
    )
}
