package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TasksServices {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task): Boolean
    suspend fun deleteTaskById(taskId: Int): Boolean
    suspend fun deleteAllTasks()
    suspend fun getTaskById(taskId: Int): Task
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: String): Flow<List<Task>>
}