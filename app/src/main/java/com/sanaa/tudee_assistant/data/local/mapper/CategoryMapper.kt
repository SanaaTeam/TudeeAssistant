package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.entity.Category
import com.sanaa.tudee_assistant.domain.entity.CategoryCreationRequest

fun CategoryLocalDto.toDomain(): Category = Category(
    id = categoryId,
    name = name,
    imagePath = imagePath,
    isDefault = isDefault
)

fun Category.toLocalDto(): CategoryLocalDto = CategoryLocalDto(
    categoryId = id,
    name = name,
    imagePath = imagePath,
    isDefault = isDefault
)


fun CategoryCreationRequest.toLocalDto(): CategoryLocalDto = CategoryLocalDto(
    categoryId = 0,
    name = name,
    imagePath = imagePath,
    isDefault = false
)

fun List<CategoryLocalDto>.toDomain(): List<Category> {
    return this.map { category -> category.toDomain() }
}
