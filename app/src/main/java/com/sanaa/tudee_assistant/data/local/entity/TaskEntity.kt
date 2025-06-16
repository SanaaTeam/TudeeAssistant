package com.sanaa.tudee_assistant.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sanaa.tudee_assistant.domain.model.Task.TaskPriority
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus
import kotlinx.datetime.LocalDate

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "task_id")
    val taskId: Int = 0,

    val title: String,
    val description: String? = null,

    @ColumnInfo(name = "status")
    val status: TaskStatus = TaskStatus.TODO,

    @ColumnInfo(name = "due_date")
    val dueDate: LocalDate? = null,

    val priority: TaskPriority = TaskPriority.MEDIUM,

    @ColumnInfo(name = "category_id")
    val categoryId: Int,

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDate

)