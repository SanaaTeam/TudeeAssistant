package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto
import com.sanaa.tudee_assistant.domain.model.NewTask
import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

fun Task.toLocalDto(): TaskLocalDto = TaskLocalDto(
    taskId = id,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = createdAt
)
fun NewTask.toLocalDto(): TaskLocalDto = TaskLocalDto(
    taskId = 0,
    title = title,
    description = description,
    status = status,
    dueDate = dueDate,
    priority = priority,
    categoryId = categoryId,
    createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
)
