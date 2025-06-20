package com.sanaa.tudee_assistant.presentation.screen.category

import com.sanaa.tudee_assistant.domain.model.Category

data class CategoryUiState(
    val currentDateCategory: List<CategoryUiModel> = emptyList(),
    val isLoading: Boolean = false,
)

data class CategoryUiModel(
    val id: Int? = null,
    val name: String,
    val imagePath: String,
    val isDefault: Boolean,
    val tasksCount: Int,
)

fun Category.toUiState(taskCount: Int): CategoryUiModel {
    return CategoryUiModel(
        name = this.name,
        imagePath = this.imagePath,
        isDefault = this.isDefault,
        tasksCount = taskCount
    )
}

fun CategoryUiModel.toCategory(): Category {
    return Category(
        name = this.name,
        imagePath = this.imagePath,
        isDefault = this.isDefault
    )
}

