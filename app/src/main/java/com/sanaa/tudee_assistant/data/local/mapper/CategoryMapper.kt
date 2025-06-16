package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.model.Category

fun CategoryLocalDto.toDomain(): Category = Category(
    id = categoryId,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)

fun Category.toEntity(): CategoryLocalDto = CategoryLocalDto(
    categoryId = id,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)
