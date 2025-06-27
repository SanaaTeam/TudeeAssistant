package com.sanaa.tudee_assistant.presentation.screen.tasks

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskViewModelTest {

    private lateinit var viewModel: TaskViewModel
    private val taskService: TaskService = mockk(relaxed = true)
    private val categoryService: CategoryService = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()
    private val fakeStatus = TaskUiStatus.TODO

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        coEvery { categoryService.getCategories() } returns flowOf(emptyList())
        coEvery { taskService.getTasksByDueDate(any()) } returns flowOf(emptyList())

        every { stringProvider.unknownError } returns UNKNOWN_ERROR
        every { stringProvider.taskDeleteSuccess } returns TASK_DELETE_SUCCESS
        every { stringProvider.taskAddedSuccess } returns TASK_ADDED_SUCCESS
        every { stringProvider.taskUpdateSuccess } returns TASK_UPDATE_SUCCESS
        every { stringProvider.taskStatusUpdateSuccess } returns TASK_STATUS_UPDATE_SUCCESS

        viewModel = TaskViewModel(taskService, categoryService, fakeStatus, stringProvider)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should set initial selectedStatusTab`() {
        assertThat(viewModel.state.value.selectedStatusTab).isEqualTo(fakeStatus)
    }


    @Test
    fun `onTaskClicked should update selectedTask and show bottom sheet`() {
        val task = fakeTasks[0]
        viewModel.onTaskClicked(task)

        assertThat(viewModel.state.value.selectedTask).isEqualTo(task)
        assertThat(viewModel.state.value.showTaskDetailsBottomSheet).isTrue()
    }

    @Test
    fun `onTaskSwipeToDelete should set selectedTask and show delete dialog`() = runTest {
        val result = viewModel.onTaskSwipeToDelete(fakeTasks[0])

        assertThat(result).isFalse()
        assertThat(viewModel.state.value.selectedTask).isEqualTo(fakeTasks[0])
        assertThat(viewModel.state.value.showDeleteTaskBottomSheet).isTrue()
    }

    @Test
    fun `onDeleteTask should delete task and show success snackbar`() = runTest {
        val task = fakeTasks[0]
        viewModel.onTaskClicked(task)
        coEvery { taskService.deleteTaskById(task.id) } returns Unit
        coEvery { taskService.getTasksByDueDate(any()) } returns flowOf(emptyList())

        viewModel.onDeleteTask()
        advanceUntilIdle()

        assertThat(viewModel.state.value.snackBarState.message).isEqualTo(TASK_DELETE_SUCCESS)
        assertThat(viewModel.state.value.showTaskDetailsBottomSheet).isFalse()
    }

    @Test
    fun `getTasksByDueDate should cancel previous job and start a new one`() = runTest {
        val firstDate = LocalDate(2025, 6, 26)
        val secondDate = LocalDate(2025, 6, 27)

        val firstCall = flow<List<Task>> {
            emit(listOf(domainTasks[0].copy(id = 1)))
            delay(100)
        }

        val secondCall = flowOf(domainTasks)

        coEvery { taskService.getTasksByDueDate(firstDate) } returns firstCall
        coEvery { taskService.getTasksByDueDate(secondDate) } returns secondCall

        viewModel.onDateSelected(firstDate)
        viewModel.onDateSelected(secondDate)
        advanceUntilIdle()

        val tasks = viewModel.state.value.currentDateTasks
        assertThat(tasks.map { it.id }).containsExactly(domainTasks[0].id)
    }

    @Test
    fun `onDeleteTask should show error snackbar on failure`() = runTest {
        val task = fakeTasks[0]
        viewModel.onTaskClicked(task)
        coEvery { taskService.deleteTaskById(any()) } throws Exception("Boom")
        every { stringProvider.unknownError } returns UNKNOWN_ERROR

        viewModel.onDeleteTask()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(UNKNOWN_ERROR)
    }

    @Test
    fun `onHideSnackBar should reset snackbar state`() {
        viewModel.onHideSnakeBar()
        val snackBar = viewModel.state.value.snackBarState
        assertThat(snackBar.message).isEmpty()
    }

    @Test
    fun `onAddTaskSuccess should show success snackbar`() {
        viewModel.onAddTaskSuccess()
        assertThat(viewModel.state.value.snackBarState.message).isEqualTo(TASK_ADDED_SUCCESS)
    }

    @Test
    fun `onEditTaskSuccess should show update snackbar`() {
        viewModel.onEditTaskSuccess()
        assertThat(viewModel.state.value.snackBarState.message).isEqualTo("Updated")
    }

    @Test
    fun `onDeleteDismiss should hide delete bottom sheet`() {
        viewModel.onDeleteDismiss()
        assertThat(viewModel.state.value.showDeleteTaskBottomSheet).isFalse()
    }

    @Test
    fun `onDismissTaskDetails should hide details bottom sheet`() {
        viewModel.onDismissTaskDetails(false)
        assertThat(viewModel.state.value.showTaskDetailsBottomSheet).isFalse()
    }

    @Test
    fun `handleOnMoveToStatusSuccess should show success snackbar`() {
        viewModel.handleOnMoveToStatusSuccess()
        assertThat(viewModel.state.value.snackBarState.message).isEqualTo(TASK_STATUS_UPDATE_SUCCESS)
    }

    @Test
    fun `handleOnMoveToStatusFail should show error`() {
        every { stringProvider.unknownError } returns UNKNOWN_ERROR

        viewModel.handleOnMoveToStatusFail()

        val snackBar = viewModel.state.value.snackBarState
        assertThat(snackBar.message).isEqualTo(UNKNOWN_ERROR)
    }

    private val fakeTasks = listOf(
        TaskUiState(
            id = 1,
            categoryId = 1,
            title = "Test Task",
            description = "Description",
            status = TaskUiStatus.TODO,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0).toString()
        )
    )

    private val domainTasks = listOf(
        Task(
            id = 1,
            title = "Test Task",
            description = "Description",
            status = Task.TaskStatus.TODO,
            dueDate = LocalDate(2025, 6, 26),
            priority = Task.TaskPriority.LOW,
            categoryId = 1,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0)
        )
    )

    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
        const val TASK_DELETE_SUCCESS = "Deleted"
        const val TASK_ADDED_SUCCESS = "Added"
        const val TASK_UPDATE_SUCCESS = "Updated"
        const val TASK_STATUS_UPDATE_SUCCESS = "Moved"
    }

}
