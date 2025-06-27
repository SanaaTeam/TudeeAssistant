package com.sanaa.tudee_assistant.data.local.mapper

import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto
import com.sanaa.tudee_assistant.domain.model.AddTaskRequest
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

fun TaskLocalDto.toDomain(): Task = Task(
    id = taskId,
    title = title,
    description = description,
    status = Task.TaskStatus.valueOf(status),
    dueDate = LocalDate.parse(dueDate),
    priority = Task.TaskPriority.valueOf(priority),
    categoryId = categoryId,
    createdAt = LocalDateTime.parse(createdAt)
)

fun Task.toLocalDto(): TaskLocalDto = TaskLocalDto(
    taskId = id,
    title = title,
    description = description,
    status = status.name,
    dueDate = dueDate.toString(),
    priority = priority.name,
    categoryId = categoryId,
    createdAt = createdAt.toString()
)

fun AddTaskRequest.toLocalDto(): TaskLocalDto = TaskLocalDto(
    taskId = 0,
    title = title,
    description = description,
    status = status.name,
    dueDate = dueDate.toString(),
    priority = priority.name,
    categoryId = categoryId,
    createdAt = DateUtil.today.toString()
)

fun List<TaskLocalDto>.toDomain(): List<Task> {
    return this.map { task -> task.toDomain() }
}