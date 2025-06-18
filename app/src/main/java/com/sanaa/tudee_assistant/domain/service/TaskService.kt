package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(taskId: Int)
    suspend fun getTaskById(taskId: Int): Task
    suspend fun getTasksCountByCategoryId(categoryId: Int): Int
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>>
}