package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.TasksServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class TasksServiceImpl(
    private val taskDao: TaskDao
) : TasksServices {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun addTask(task: Task): Boolean {
        return taskDao.insertTask(task.toLocalDto()) != -1L
    }

    override suspend fun updateTask(task: Task): Boolean {
        return taskDao.updateTask(task.toLocalDto()) > 0
    }

    override suspend fun deleteTaskById(taskId: Int): Boolean {
        return taskDao.deleteTaskById(taskId) > 0
    }

    override suspend fun deleteAllTasks(): Boolean {
        return taskDao.deleteAllTasks() > 0
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)?.toDomain()
    }

    override fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTasksByCategoryId(categoryId).map { list -> list.map { it.toDomain() } }

    }

    override fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status).map { list -> list.map { it.toDomain() } }
    }

    override fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>> {
        return taskDao.getTasksByDueDate(dueDate).map { list -> list.map { it.toDomain() } }

    }

}
