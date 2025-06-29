package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.entity.Category
import com.sanaa.tudee_assistant.domain.entity.CategoryCreationRequest
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState

fun CategoryUiState.toDomain(): Category {
    return Category(
        id = id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}

fun CategoryUiState.toCreationRequest(): CategoryCreationRequest {
    return CategoryCreationRequest(
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

fun List<Category>.toState(tasksCount: Int): List<CategoryUiState> {
    return this.map { it.toState(tasksCount) }
}