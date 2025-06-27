package com.sanaa.tudee_assistant.data.services

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.domain.model.AddTaskRequest
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.TaskService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TasksServiceImplTest {
    private val taskDao = mockk<TaskDao>(relaxed = true)
    private lateinit var tasksService: TaskService

    @BeforeEach
    fun setUp() {
        tasksService = TaskServiceImpl(taskDao)
    }


    @Test
    fun `addTask should invoke insertTask in taskDao with correct task`() = runTest {
        // Given
        coEvery { taskDao.insertTask(any()) } returns 1

        // When
        tasksService.addTask(fakeTasks[0].toNewTask())

        // Then
        coVerify(exactly = 1) { taskDao.insertTask(match { it.title == fakeTasks[0].title }) }
    }

    @Test
    fun `addTask should throw FailedToAddTaskException when insertTask fails`() = runTest {
        // Given
        coEvery { taskDao.insertTask(any()) } returns -1
        // When
        val result = runCatching { tasksService.addTask(fakeTasks[0].toNewTask()) }
        // Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `updateTask should invoke updateTask in taskDao with correct task`() = runTest {
        // Given
        coEvery { taskDao.updateTask(any()) } returns 1
        // When
        tasksService.updateTask(fakeTasks[0].toDomain())
        // Then
        coVerify(exactly = 1) { taskDao.updateTask(match { it.taskId == fakeTasks[0].taskId }) }

    }

    @Test
    fun `updateTask should throw FailedToUpdateTaskException when updateTask fails`() = runTest {
        // Given
        coEvery { taskDao.updateTask(any()) } returns 0
        // When
        val result = runCatching { tasksService.updateTask(fakeTasks[0].toDomain()) }
        // Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `deleteTaskById should invoke deleteTaskById in taskDao with correct taskId`() = runTest {
        // Given
        coEvery { taskDao.deleteTaskById(any()) } returns 1
        // When
        tasksService.deleteTaskById(fakeTasks[0].taskId)
        // Then
        coVerify(exactly = 1) { taskDao.deleteTaskById(fakeTasks[0].taskId) }
    }

    @Test
    fun `deleteTaskById should throw FailedToDeleteTaskException when deleteTaskById fails`() =
        runTest {
            // Given
            coEvery { taskDao.deleteTaskById(any()) } returns 0

            // When
            val result = runCatching { tasksService.deleteTaskById(fakeTasks[0].taskId) }
            // Then
            assertThat(result.isFailure).isTrue()
        }

    @Test
    fun `getTaskById should invoke getTaskById in taskDao with correct taskId`() = runTest {
        val taskFlow = flowOf(fakeTasks[0])
        // Given
        coEvery { taskDao.getTaskById(any()) } returns taskFlow
        // When
        tasksService.getTaskById(fakeTasks[0].taskId)
        // Then
        coVerify(exactly = 1) { taskDao.getTaskById(fakeTasks[0].taskId) }
    }

    @Test
    fun `getTasksByCategoryId should invoke getTasksByCategoryId in taskDao with correct categoryId`() =
        runTest {
            // Given
            every { taskDao.getTasksByCategoryId(any()) } returns flowOf(fakeTasks)
            // When
            tasksService.getTasksByCategoryId(fakeTasks[0].categoryId).toList()
            // Then
            coVerify(exactly = 1) { taskDao.getTasksByCategoryId(fakeTasks[0].categoryId) }
        }

    @Test
    fun `getTasksByDueDate should invoke getTasksByDueDate in taskDao with correct dueDate`() =
        runTest {
            // Given
            every { taskDao.getTasksByDate(any()) } returns flowOf(fakeTasks)
            // When
            tasksService.getTasksByDueDate(LocalDate.parse(fakeTasks[0].dueDate)).toList()
            // Then
            coVerify(exactly = 1) {
                taskDao.getTasksByDate(fakeTasks[0].dueDate)
            }
        }

    @Test
    fun `getTasksByDueDate should throw DatabaseFailureException when getTasksByDate fails`() =
        runTest {
            // Given
            every { taskDao.getTasksByDate(any()) } throws Exception("Database error")
            // When
            val result = runCatching {
                tasksService.getTasksByDueDate(LocalDate.parse(fakeTasks[0].dueDate)).toList()
            }
            // Then
            assertThat(result.isFailure).isTrue()
        }

    private val fakeTasks = listOf(
        TaskLocalDto(
            taskId = 1,
            title = "Task 1",
            description = "Description 1",
            status = Task.TaskStatus.IN_PROGRESS.name,
            dueDate = LocalDate(2023, 1, 1).toString(),
            priority = Task.TaskPriority.HIGH.name,
            categoryId = 1,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
        )
    )

    private fun TaskLocalDto.toNewTask(): AddTaskRequest {
        return AddTaskRequest(
            title = title,
            description = description,
            status = Task.TaskStatus.valueOf(status),
            dueDate = LocalDate.parse(dueDate),
            priority = Task.TaskPriority.valueOf(priority),
            categoryId = categoryId
        )
    }
}