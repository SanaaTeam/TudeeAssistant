package com.sanaa.tudee_assistant.data.services

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.data.local.dao.TaskDao
import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.domain.model.Task
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
import org.junit.jupiter.api.Test

class TasksServiceImplTest {

    private val taskDao = mockk<TaskDao>(relaxed = true)
    private val tasksService = TasksServiceImpl(taskDao)


    @Test
    fun `getAllTasks should return list of task when database is not empty`() = runTest {
        // Given
        every { taskDao.getAllTasks() } returns flowOf(fakeTasks)

        // When
        val result = tasksService.getAllTasks().toList()

        // Then
        assertThat(result).isNotEmpty()
    }

    @Test
    fun `getAllTasks should return empty list when database is empty`() = runTest {
        // Given
        every { taskDao.getAllTasks() } returns flowOf()

        // When
        val result = tasksService.getAllTasks().toList()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `getAllTasks should throw DatabaseFailureException when database fails`() = runTest {
        // Given
        every { taskDao.getAllTasks() } throws Exception("Database error")

        // When
        val result = kotlin.runCatching { tasksService.getAllTasks().toList() }

        // Then
        assertThat(result.isFailure).isTrue()
    }

    @Test
    fun `addTask should invoke insertTask in taskDao with correct task`() = runTest {
        // Given
        coEvery { taskDao.insertTask(any()) } returns 1

        // When
        tasksService.addTask(fakeTasks[0].toDomain())

        // Then
        coVerify(exactly = 1) { taskDao.insertTask(match { it.taskId == fakeTasks[0].taskId }) }
    }

    @Test
    fun `addTask should throw FailedToAddTaskException when insertTask fails`() = runTest {
        // Given
        coEvery { taskDao.insertTask(any()) } returns -1
        // When
        val result = runCatching { tasksService.addTask(fakeTasks[0].toDomain()) }
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
    fun `deleteAllTasks should invoke deleteAllTasks in taskDao`() = runTest {
        // Given
        coEvery { taskDao.deleteAllTasks() } returns 1
        // When
        tasksService.deleteAllTasks()
        // Then
        coVerify(exactly = 1) { taskDao.deleteAllTasks() }

    }

    @Test
    fun `deleteAllTasks should throw FailedToDeleteTaskException when deleteAllTasks fails`() =
        runTest {
            // Given
            coEvery { taskDao.deleteAllTasks() } returns 0
            // When
            val result = runCatching { tasksService.deleteAllTasks() }
            // Then
            assertThat(result.isFailure).isTrue()
        }

    @Test
    fun `getTaskById should invoke getTaskById in taskDao with correct taskId`() = runTest {
        // Given
        coEvery { taskDao.getTaskById(any()) } returns fakeTasks[0]
        // When
        tasksService.getTaskById(fakeTasks[0].taskId)
        // Then
        coVerify(exactly = 1) { taskDao.getTaskById(fakeTasks[0].taskId) }
    }

    @Test
    fun `getTaskById should throw TaskNotFoundException when getTaskById returns null`() = runTest {
        // Given
        coEvery { taskDao.getTaskById(any()) } returns null
        // When
        val result = runCatching { tasksService.getTaskById(fakeTasks[0].taskId) }
        // Then
        assertThat(result.isFailure).isTrue()
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
    fun `getTasksByStatus should invoke getTasksByStatus in taskDao with correct status`() =
        runTest {
            // Given
            every { taskDao.getTasksByStatus(any()) } returns flowOf(fakeTasks)
            // When
            tasksService.getTasksByStatus(fakeTasks[0].status).toList()
            // Then
            coVerify(exactly = 1) { taskDao.getTasksByStatus(fakeTasks[0].status) }
        }

    @Test
    fun `getTasksByDueDate should invoke getTasksByDueDate in taskDao with correct dueDate`() =
        runTest {
            // Given
            every { taskDao.getTasksByDate(any()) } returns flowOf(fakeTasks)
            // When
            tasksService.getTasksByDueDate(fakeTasks[0].dueDate ?: LocalDate(2023, 1, 1)).toList()
            // Then
            coVerify(exactly = 1) {
                taskDao.getTasksByDate(
                    fakeTasks[0].dueDate ?: LocalDate(
                        2023,
                        1,
                        1
                    )
                )
            }
        }

    @Test
    fun `getTasksByDueDate should throw DatabaseFailureException when getTasksByDate fails`() =
        runTest {
            // Given
            every { taskDao.getTasksByDate(any()) } throws Exception("Database error")
            // When
            val result = runCatching {
                tasksService.getTasksByDueDate(
                    fakeTasks[0].dueDate ?: LocalDate(
                        2023,
                        1,
                        1
                    )
                ).toList()
            }
            // Then
            assertThat(result.isFailure).isTrue()
        }


    val fakeTasks = listOf(
        TaskLocalDto(
            taskId = 1,
            title = "Task 1",
            description = "Description 1",
            status = Task.TaskStatus.IN_PROGRESS,
            dueDate = LocalDate(2023, 1, 1),
            priority = Task.TaskPriority.HIGH,
            categoryId = 1,
            createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        )
    )
}