package com.sanaa.tudee_assistant.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "task_id")
    val taskId: Int = 0

)