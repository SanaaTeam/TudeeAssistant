package com.sanaa.tudee_assistant.presentation.state.mapper

import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

fun CategoryUiState.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}

fun CategoryUiState.toNewCategory(): AddCategoryRequest {
    return AddCategoryRequest(
        name = name,
        imagePath = imagePath,
    )
}

fun Category.toState(tasksCount: Int): CategoryUiState {
    return CategoryUiState(
        id = id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault,
        tasksCount = tasksCount
    )
}

fun List<Category>.toStateList(tasksCount: Int): List<CategoryUiState> {
    return this.map { it.toState(tasksCount) }
}