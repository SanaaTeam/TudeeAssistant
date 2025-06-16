package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.entity.TaskLocalDto
import com.sanaa.tudee_assistant.domain.model.Task

fun TaskLocalDto.toDomain(): Task = Task(
    id = taskId,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = createdAt
)

fun Task.toEntity(): TaskLocalDto = TaskLocalDto(
    taskId = id,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = createdAt
)
