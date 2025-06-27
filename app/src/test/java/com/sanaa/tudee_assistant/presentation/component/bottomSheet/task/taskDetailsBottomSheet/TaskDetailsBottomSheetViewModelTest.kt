package com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet

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
import kotlinx.coroutines.flow.flowOf
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

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineRule::class)
class TaskDetailsBottomSheetViewModelTest {
    private lateinit var taskService: TaskService
    private lateinit var categoryService: CategoryService
    private lateinit var stringProvider: StringProvider
    private lateinit var viewModel: TaskDetailsBottomSheetViewModel
    val testDispatcher = StandardTestDispatcher()


    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        taskService = mockk(relaxed = true)
        categoryService = mockk()
        stringProvider = mockk()
        viewModel = TaskDetailsBottomSheetViewModel(taskService, categoryService, stringProvider,testDispatcher)
    }


    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getSelectedTask updates state correctly`() = runTest {
        // given
        val taskFlow = flowOf(todoTask)
        coEvery { taskService.getTaskById(1) } returns taskFlow
        coEvery { categoryService.getCategoryById(100).imagePath } returns "images/path.png"
        every { stringProvider.markAsInProgress } returns "Mark as In Progress"

        // when
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        // then
        val state = viewModel.state.value
        assertThat(state.id).isEqualTo(todoTask.id)
        assertThat(state.title).isEqualTo(todoTask.title)
        assertThat(state.description).isEqualTo(todoTask.description)
        assertThat(state.status.name).isEqualTo(todoTask.status.name)
        assertThat(state.categoryImagePath).isEqualTo("images/path.png")
        assertThat(state.moveStatusToLabel).isEqualTo("Mark as In Progress")
    }

    @Test
    fun `onMoveTaskToAnotherStatus change the status in todo to in progress`() = runTest {
        // given
        val taskFlow = flowOf(task.copy(status = TaskStatus.TODO))
        coEvery { taskService.getTaskById(task.id) } returns taskFlow
        coEvery { categoryService.getCategoryById(categoryId) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsInProgress } returns "Move to in progress"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        viewModel.onMoveTaskToAnotherStatus(onMoveStatusFail = {}, onMoveStatusSuccess = {})
        advanceUntilIdle()

        // Assert
       assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.IN_PROGRESS.name)
    }


    @Test
    fun `onMoveTaskToAnotherStatus change the status in  in progress to done `() = runTest {
        // given
        val taskFlow = flowOf(todoTask.copy(status = TaskStatus.IN_PROGRESS))
        coEvery { taskService.getTaskById(todoTask.id) } returns taskFlow
        coEvery { categoryService.getCategoryById(categoryId = categoryId) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsDone } returns "Move to done"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        viewModel.onMoveTaskToAnotherStatus(onMoveStatusFail = {}, onMoveStatusSuccess = {})
        advanceUntilIdle()

        // Assert
        assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.DONE.name)
    }

    @Test
    fun `onMoveTaskToAnotherStatus change the status done  `() = runTest {
        // given
        val taskFlow = flowOf(task)
        coEvery { taskService.getTaskById(task.id) } returns taskFlow
        coEvery { categoryService.getCategoryById(categoryId = categoryId) } returns Category(
            name = "test",
            imagePath = "images/path.png",
            isDefault = true,
            id = 100
        )
        every { stringProvider.markAsDone } returns "Move to done"
        viewModel.getSelectedTask(1)
        advanceUntilIdle()

        //when

        viewModel.onMoveTaskToAnotherStatus(onMoveStatusFail = {}, onMoveStatusSuccess = {})
        advanceUntilIdle()

        // Assert
        assertThat(viewModel.state.value.status.name).isEqualTo(TaskUiStatus.DONE.name)
    }


    companion object{
        val categoryId = 100
        val task = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.DONE,
            categoryId = categoryId,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

        val todoTask = Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = TaskStatus.TODO,
            categoryId = categoryId,
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
            priority = Task.TaskPriority.LOW,
            createdAt = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )

    }
}