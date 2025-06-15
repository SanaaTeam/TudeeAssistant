package com.sanaa.tudee_assistant.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import com.sanaa.tudee_assistant.data.datasource.local.entity.TaskEntity

@Dao
interface TaskDao {
    @Insert
    fun insert(task: TaskEntity)
}