package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest

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


fun AddCategoryRequest.toLocalDto(): CategoryLocalDto = CategoryLocalDto(
    categoryId = 0,
    name = name,
    imagePath = imagePath,
    isDefault = false
)
