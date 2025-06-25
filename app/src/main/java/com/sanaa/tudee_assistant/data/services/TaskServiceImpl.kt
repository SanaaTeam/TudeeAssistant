package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateException
import com.sanaa.tudee_assistant.domain.exceptions.NotFoundException
import com.sanaa.tudee_assistant.domain.model.AddTaskRequest
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class TaskServiceImpl(
    private val taskDao: TaskDao,
) : TaskService {
    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { list ->
                list.map { it.toDomain() }
            }
    }

    override suspend fun addTask(addTaskRequest: AddTaskRequest) {
        if (taskDao.insertTask(addTaskRequest.toLocalDto()) == -1L) {
            throw FailedToAddException("Failed to add task")
        }
    }

    override suspend fun updateTask(task: Task) {
        if (taskDao.updateTask(task.toLocalDto()) <= 0) {
            throw FailedToUpdateException("Failed to update task")
        }

    }

    override suspend fun deleteTaskById(taskId: Int) {
        if (taskDao.deleteTaskById(taskId) <= 0) {
            throw FailedToDeleteException("Failed to delete task")
        }
    }

    override suspend fun deleteAllTasks() {
        if (taskDao.deleteAllTasks() <= 0) {
            throw FailedToDeleteException("Failed to delete all tasks")
        }
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return taskDao
            .getTaskById(taskId)
            ?.toDomain()
            ?: throw NotFoundException("Task not found")
    }


    override fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTasksByCategoryId(categoryId).map { list ->
            list.map { it.toDomain() }
        }
    }


    override fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>> {
        return taskDao.getTasksByDate(dueDate.toString()).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTaskCountByCategoryId(categoryId: Int): Flow<Int> {
        return taskDao.getTaskCountByCategoryId(categoryId)
    }

    override fun getTaskCountsGroupedByCategoryId(): Flow<Map<Int, Int>> {
        return taskDao.getTaskCountsGroupedByCategoryId()
            .map { list ->
                list.associate { it.categoryId to it.count }
            }
    }

}
