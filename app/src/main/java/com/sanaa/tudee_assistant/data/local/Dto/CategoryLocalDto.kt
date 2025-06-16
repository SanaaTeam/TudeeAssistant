package com.sanaa.tudee_assistant.data.local.Dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryLocalDto(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0,

    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "is_default")
    val isDefault: Boolean = false

)