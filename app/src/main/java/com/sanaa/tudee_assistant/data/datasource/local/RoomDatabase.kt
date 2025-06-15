package com.sanaa.tudee_assistant.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sanaa.tudee_assistant.data.datasource.local.converters.Converters
import com.sanaa.tudee_assistant.data.datasource.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.datasource.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.datasource.local.entity.CategoryEntity
import com.sanaa.tudee_assistant.data.datasource.local.entity.TaskEntity

@Database(
    entities = [TaskEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}