package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.exceptions.DatabaseFailureException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddTaskException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteTaskException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateTaskException
import com.sanaa.tudee_assistant.domain.exceptions.TaskNotFoundException
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.TaskService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class TasksServiceImpl(
    private val taskDao: TaskDao
) : TaskService {

    override fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { list ->
                list.map { it.toDomain() }
            }.catch {
                throw DatabaseFailureException(
                    message = "Failed to load all Tasks from database duo to database error details :${it.message} ",
                    cause = it
                )
            }

    }


    override suspend fun addTask(task: Task) {
        if (taskDao.insertTask(task.toLocalDto()) == -1L) {
            throw FailedToAddTaskException()
        }
    }

    override suspend fun updateTask(task: Task) {
        if (taskDao.updateTask(task.toLocalDto()) <= 0) {
            throw FailedToUpdateTaskException()
        }

    }

    override suspend fun deleteTaskById(taskId: Int) {
        if (taskDao.deleteTaskById(taskId) <= 0) {
            throw FailedToDeleteTaskException()
        }
    }

    override suspend fun deleteAllTasks() {
        if (taskDao.deleteAllTasks() <= 0) {
            throw FailedToDeleteTaskException()
        }
    }

    override suspend fun getTaskById(taskId: Int): Task {
        return taskDao.getTaskById(taskId)?.toDomain() ?: throw TaskNotFoundException()
    }


    override fun getTasksByCategoryId(categoryId: Int): Flow<List<Task>> {
        return taskDao.getTasksByCategoryId(categoryId).map { list ->
            list.map { it.toDomain() }
        }.catch {
            throw DatabaseFailureException("database failure", it)
        }
    }


    override fun getTasksByStatus(status: Task.TaskStatus): Flow<List<Task>> {
        return taskDao.getTasksByStatus(status).map { list ->
            list.map { it.toDomain() }
        }.catch {
            throw DatabaseFailureException("database failure", it)
        }
    }

    override fun getTasksByDueDate(dueDate: LocalDate): Flow<List<Task>> {
        return taskDao.getTasksByDate(dueDate).map { list ->
            list.map { it.toDomain() }
        }.catch {
            throw DatabaseFailureException("database failure", it)
        }
    }

    override fun getTaskCountByCategoryId(categoryId: Int): Flow<Int> {
        return taskDao.getTaskCountByCategoryId(categoryId)
    }

}
