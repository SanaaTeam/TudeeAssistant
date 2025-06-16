package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.entity.TaskEntity
import com.sanaa.tudee_assistant.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(
    id = taskId,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = createdAt
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    taskId = id,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = createdAt
)
