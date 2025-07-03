package com.sanaa.tudee_assistant.presentation.screen.home

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toDomain
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class HomeScreenViewModelTest {
    private lateinit var viewModel: HomeScreenViewModel
    private val categoryService: CategoryService = mockk(relaxed = true)
    private val taskService: TaskService = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk(relaxed = true)
    private val preferencesManager: PreferencesManager = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)
        every { stringProvider.unknownError } returns UNKNOWN_ERROR
        every { stringProvider.taskDeleteSuccess } returns TASK_DELETE_SUCCESS
        every { stringProvider.taskAddedSuccess } returns TASK_ADDED_SUCCESS
        every { stringProvider.taskUpdateSuccess } returns TASK_UPDATE_SUCCESS
        every { stringProvider.taskStatusUpdateSuccess } returns TASK_STATUS_UPDATE_SUCCESS
        every { stringProvider.taskStatusUpdateError } returns TASK_STATUS_UPDATE_ERROR

        coEvery { preferencesManager.isDarkTheme } returns flowOf(true)

        coEvery { taskService.getTasksByDueDate(any()) } returns flowOf(emptyList())
        coEvery { categoryService.getCategories() } returns flowOf(emptyList())
        coEvery { categoryService.getCategories() } returns flowOf(emptyList())

        viewModel = HomeScreenViewModel(
            preferencesManager = preferencesManager,
            taskService = taskService,
            categoryService = categoryService,
            stringProvider = stringProvider
        )
    }

    val dummyTask = TaskUiState(
        id = 1,
        categoryId = 1,
        title = "Test Task",
        description = "Description",
        status = TaskUiStatus.TODO,
        createdAt = LocalDateTime(
            2025,
            6,
            1,
            12,
            0
        ).toString()
    )

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onAddTaskSuccess should update state and reload tasks`() = runTest {
        viewModel.onAddTaskSuccess()

        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo(TASK_ADDED_SUCCESS)
        assertThat(state.snackBarState.isVisible).isTrue()
    }

    @Test
    fun `onEditTaskError should show error snackbar`() = runTest {
        val errorMessage = "Something went wrong"
        viewModel.onEditTaskError(errorMessage)

        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo(errorMessage)
        assertThat(state.snackBarState.isVisible).isTrue()
    }

    @Test
    fun `onToggleColorTheme should change theme setting`() = runTest {
        coEvery { preferencesManager.setDarkTheme(any()) } just Runs
        viewModel.onToggleColorTheme()

        coVerify { preferencesManager.setDarkTheme(false) }
    }

    @Test
    fun `onShowTaskDetails should update showTaskDetailsBottomSheet to true`() = runTest {
        viewModel.onShowTaskDetails(true)
        val state = viewModel.state.value

        assertThat(state.showTaskDetailsBottomSheet).isTrue()
    }

    @Test
    fun `onShowAddTaskSheet should set showAddTaskSheet to true`() = runTest {
        viewModel.onShowAddTaskSheet()
        assertThat(viewModel.state.value.showAddTaskSheet).isTrue()
    }

    @Test
    fun `onTaskClick should set selected task and show task details`() = runTest {
        viewModel.onTaskClick(dummyTask)

        val state = viewModel.state.value
        assertThat(state.selectedTask).isEqualTo(dummyTask)
        assertThat(state.showTaskDetailsBottomSheet).isTrue()
    }

    @Test
    fun `onAddTaskError should show error snackbar`() = runTest {
        val errorMessage = TASK_STATUS_UPDATE_ERROR
        viewModel.onAddTaskError(errorMessage)

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(errorMessage)
        assertThat(state.snackBarState.isVisible).isTrue()
    }

    @Test
    fun `onEditTaskSuccess should reload tasks and show success snackbar`() = runTest {
        viewModel.onEditTaskSuccess()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(TASK_UPDATE_SUCCESS)
        assertThat(state.snackBarState.isVisible).isTrue()
    }

    @Test
    fun `onHideSnackBar should hide snackbar`() = runTest {
        viewModel.onHideSnackBar()

        val state = viewModel.state.value
        assertThat(state.snackBarState.isVisible).isFalse()
    }

    @Test
    fun `onHideEditTaskSheet should reset edit sheet state`() = runTest {
        viewModel.onHideEditTaskSheet()

        val state = viewModel.state.value
        assertThat(state.showEditTaskSheet).isFalse()
        assertThat(state.taskToEdit).isNull()
    }

    @Test
    fun `onShowEditTaskSheet should show edit sheet with selected task`() = runTest {
        viewModel.onShowEditTaskSheet(dummyTask)

        val state = viewModel.state.value
        assertThat(state.showEditTaskSheet).isTrue()
        assertThat(state.taskToEdit).isEqualTo(dummyTask)
    }

    @Test
    fun `onNavigateToTaskScreen should call changeTaskStatus with correct domain status`() = runTest {
        val uiStatus = TaskUiStatus.DONE
        val domainStatus = uiStatus.toDomain()
        coEvery { preferencesManager.changeTaskStatus(domainStatus) } just Runs
        viewModel.onNavigateToTaskScreen(uiStatus)
        coVerify(exactly = 1) { preferencesManager.changeTaskStatus(domainStatus) }
    }
    @Test
    fun `onHideAddTaskSheet should set showAddTaskSheet to false`() = runTest {
        viewModel.onShowAddTaskSheet()
        assertThat(viewModel.state.value.showAddTaskSheet).isTrue()
        viewModel.onHideAddTaskSheet()
        val state = viewModel.state.value
        assertThat(state.showAddTaskSheet).isFalse()
    }
    @Test
    fun `onMoveStatusSuccess should reload tasks, show success snackbar and hide bottom sheet`() = runTest {
        coEvery { taskService.getTasksByDueDate(any()) } returns flowOf(emptyList())

        viewModel.onMoveStatusSuccess()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(TASK_STATUS_UPDATE_SUCCESS)
        assertThat(state.snackBarState.isVisible).isTrue()
        assertThat(state.showTaskDetailsBottomSheet).isFalse()
    }
    @Test
    fun `onMoveStatusFail should show error snackbar with correct message`() = runTest {
        viewModel.onMoveStatusFail()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(TASK_STATUS_UPDATE_ERROR)
        assertThat(state.snackBarState.isVisible).isTrue()
    }



    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
        const val TASK_DELETE_SUCCESS = "Deleted task successfully."
        const val TASK_ADDED_SUCCESS = "Task added successfully!"
        const val TASK_UPDATE_SUCCESS = "Edited task successfully."
        const val TASK_STATUS_UPDATE_SUCCESS = "task status updated successfully."
        const val TASK_STATUS_UPDATE_ERROR = "Fail Updating Task status."
    }

}


