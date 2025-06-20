package com.sanaa.tudee_assistant.presentation.model.mapper

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryUiModel

fun CategoryUiModel.toCategory(): Category {
    return Category(
        id = this.id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault
    )
}

fun Category.toState(tasksCount: Int): CategoryUiModel {
    return CategoryUiModel(
        id = this.id,
        name = name,
        imagePath = imagePath,
        isDefault = isDefault,
        tasksCount = tasksCount
    )
}