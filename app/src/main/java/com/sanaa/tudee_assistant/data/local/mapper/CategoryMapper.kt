package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.entity.CategoryEntity
import com.sanaa.tudee_assistant.domain.model.Category

fun CategoryEntity.toDomain(): Category = Category(
    id = categoryId,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)

fun Category.toEntity(): CategoryEntity = CategoryEntity(
    categoryId = id,
    name = name,
    imageUrl = imageUrl,
    isDefault = isDefault
)
