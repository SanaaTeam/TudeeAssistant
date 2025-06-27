package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState

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