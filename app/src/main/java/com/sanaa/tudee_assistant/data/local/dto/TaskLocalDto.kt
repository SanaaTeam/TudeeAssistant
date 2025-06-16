package com.sanaa.tudee_assistant.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus

import kotlinx.datetime.LocalDateTime

@Entity(tableName = "tasks")
data class TaskLocalDto(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "task_id")
    val taskId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "status")
    val status: TaskStatus = TaskStatus.TODO,

    @ColumnInfo(name = "due_date")
    val dueDate: LocalDate? = null,
  
    @ColumnInfo(name = "priority")
    val priority: TaskPriority = TaskPriority.MEDIUM,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime

)