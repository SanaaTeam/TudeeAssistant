package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TasksServices {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun addTask(task: Task): Boolean
    suspend fun updateTask(task: Task): Boolean
    suspend fun deleteTaskById(taskId: Int): Boolean
    suspend fun deleteAllTasks(): Boolean
    suspend fun getTaskById(taskId: Int): Task?
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>>
}