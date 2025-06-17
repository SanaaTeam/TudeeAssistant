package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskService {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(taskId: Int)
    suspend fun deleteAllTasks()
    suspend fun getTaskById(taskId: Int): Task
    suspend fun getTasksCountByCategory(categoryId: Int): Int
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: String): Flow<List<Task>>
}