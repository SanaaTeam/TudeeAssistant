package com.sanaa.tudee_assistant.presentation.screens.category

import com.sanaa.tudee_assistant.domain.model.Category
import kotlinx.coroutines.flow.Flow

data class CategoryUiState(
    val currentDateCategory: List<CategoryUiModel> = emptyList(),
    val isLoading: Boolean = false,
)

data class CategoryUiModel(
    val name: String,
    val imageUrl: String,
    val isDefault: Boolean,
    val tasksCount: Int,
)

fun Category.toUiState(taskCount: Int): CategoryUiModel {
    return CategoryUiModel(
        name = this.name,
        imageUrl = this.imagePath,
        isDefault = this.isDefault,
        tasksCount = taskCount
    )
}

fun CategoryUiModel.toCategory(): Category {
    return Category(
        name = this.name,
        imagePath = this.imageUrl,
        isDefault = this.isDefault
    )
}

