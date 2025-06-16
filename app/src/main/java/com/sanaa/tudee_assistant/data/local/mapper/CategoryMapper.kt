package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.Dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.model.Category

fun CategoryLocalDto.toDomain(): Category = Category(
    id = categoryId,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)

fun Category.toLocalDto(): CategoryLocalDto = CategoryLocalDto(
    categoryId = id,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)
