package com.sanaa.tudee_assistant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto

@Database(
    entities = [TaskLocalDto::class, CategoryLocalDto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
}