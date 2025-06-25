package com.sanaa.tudee_assistant.data.local.dto

import androidx.room.ColumnInfo

data class CategoryTaskCountDto(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "count")
    val count: Int
)