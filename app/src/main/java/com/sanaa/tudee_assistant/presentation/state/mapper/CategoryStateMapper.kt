package com.sanaa.tudee_assistant.presentation.state.mapper

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.NewCategory
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

fun CategoryUiState.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}

fun CategoryUiState.toNewCategory(): NewCategory {
    return NewCategory(
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