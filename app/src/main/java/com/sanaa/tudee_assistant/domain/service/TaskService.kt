package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.entity.Task
import com.sanaa.tudee_assistant.domain.entity.TaskCreationRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface TaskService {
    suspend fun addTask(taskCreationRequest: TaskCreationRequest)
    suspend fun updateTask(task: Task)
    suspend fun deleteTaskById(taskId: Int)
    suspend fun deleteTaskByCategoryId(categoryId: Int)
    suspend fun getTaskById(taskId: Int): Flow<Task?>
    fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>>
    fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>>
    fun getTaskCountsGroupedByCategoryId(): Flow<Map<Int, Int>>
}