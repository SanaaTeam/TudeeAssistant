package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.AddTaskRequest
import com.sanaa.tudee_assistant.domain.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun addTask(addTaskRequest: AddTaskRequest)
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(taskId: Int)
    suspend fun deleteTaskByCategoryId(categoryId: Int)
    suspend fun deleteAllTasks()
    suspend fun getTaskById(taskId: Int): Task
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>>
    fun getTaskCountByCategoryId(categoryId: Int): Flow<Int>
    fun getTaskCountsGroupedByCategoryId(): Flow<Map<Int, Int>>
}