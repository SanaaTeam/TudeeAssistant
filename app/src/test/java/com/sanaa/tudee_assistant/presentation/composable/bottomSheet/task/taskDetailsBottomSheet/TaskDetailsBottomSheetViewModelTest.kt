package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.MainCoroutineRule
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.model.Task.TaskStatus
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MainCoroutineRule::class)
class TaskDetailsBottomSheetViewModelTest {
    private lateinit var taskService: TaskService
    private lateinit var categoryService: CategoryService
    private lateinit var stringProvider: StringProvider
    private lateinit var viewModel: TaskDetailsBottomSheetViewModel
    val testDispatcher = StandardTestDispatcher()


    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        taskService = mockk(relaxed = true)
        categoryService = mockk()
        stringProvider = mockk()
        viewModel = TaskDetailsBottomSheetViewModel(taskService, categoryService, stringProvider,testDispatcher)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getSelectedTask updates state correctly`() = runTest {
        // given
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.TODO,
            categoryId = 100,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

        coEvery { taskService.getTaskById(1) } returns task
        coEvery { categoryService.getCategoryById(100).imagePath } returns "images/path.png"
        every { stringProvider.markAsInProgress } returns "Mark as In Progress"

        // when
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        // then
        val state = viewModel.state.value
        assertThat(state.id).isEqualTo(task.id)
        assertThat(state.title).isEqualTo(task.title)
        assertThat(state.description).isEqualTo(task.description)
        assertThat(state.status.name).isEqualTo(task.status.name)
        assertThat(state.categoryImagePath).isEqualTo("images/path.png")
        assertThat(state.moveStatusToLabel).isEqualTo("Mark as In Progress")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onMoveTaskToAnotherStatus chang the status in todo to in progress `() = runTest {
        // given
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.TODO,
            categoryId = 100,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

        coEvery { taskService.getTaskById(1) } returns task
        coEvery { categoryService.getCategoryById(100) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsInProgress } returns "Move to in progress"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        var successCalled = false
        var failCalled = false
        viewModel.onMoveTaskToAnotherStatus(
            onMoveStatusSuccess = { successCalled = true },
            onMoveStatusFail = { failCalled = true }
        )
        advanceUntilIdle()

        // Assert
       assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.IN_PROGRESS.name)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onMoveTaskToAnotherStatus chang the status in  in progress to done `() = runTest {
        // given
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.IN_PROGRESS,
            categoryId = 100,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

        coEvery { taskService.getTaskById(1) } returns task
        coEvery { categoryService.getCategoryById(100) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsDone } returns "Move to done"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        var successCalled = false
        var failCalled = false
        viewModel.onMoveTaskToAnotherStatus(
            onMoveStatusSuccess = { successCalled = true },
            onMoveStatusFail = { failCalled = true }
        )
        advanceUntilIdle()

        // Assert
        assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.DONE.name)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `onMoveTaskToAnotherStatus chang the status done  `() = runTest {
        // given
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.DONE,
            categoryId = 100,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

        coEvery { taskService.getTaskById(1) } returns task
        coEvery { categoryService.getCategoryById(100) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsDone } returns "Move to done"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        var successCalled = false
        var failCalled = false
        viewModel.onMoveTaskToAnotherStatus(
            onMoveStatusSuccess = { successCalled = true },
            onMoveStatusFail = { failCalled = true }
        )
        advanceUntilIdle()

        // Assert
        assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.DONE.name)
    }


}