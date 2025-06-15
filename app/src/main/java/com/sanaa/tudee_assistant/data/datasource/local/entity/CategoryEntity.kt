package com.sanaa.tudee_assistant.data.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "category_id")
    val categoryId: Int = 0

)