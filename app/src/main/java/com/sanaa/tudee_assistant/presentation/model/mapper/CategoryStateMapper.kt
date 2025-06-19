package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState

fun CategoryUiState.toCategory(): Category{
    return Category(
        id = this.id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}
fun Category.toState(): CategoryUiState{
    return CategoryUiState(
        id = this.id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}