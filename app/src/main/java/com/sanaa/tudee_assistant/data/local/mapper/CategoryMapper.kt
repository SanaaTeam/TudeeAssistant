package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.model.Category

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
