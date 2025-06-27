package com.sanaa.tudee_assistant.presentation.screen.home

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.screen.tasks.TaskViewModelTest
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
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

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onAddTaskSuccess should update state and reload tasks`() = runTest {
        viewModel.onAddTaskSuccess()

        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo("Added")
        assertThat(state.snackBarState.isVisible).isTrue()
    }


    private companion object {
        const val UNKNOWN_ERROR = "Unknown error"
        const val TASK_DELETE_SUCCESS = "Deleted"
        const val TASK_ADDED_SUCCESS = "Added"
        const val TASK_UPDATE_SUCCESS = "Updated"
        const val TASK_STATUS_UPDATE_SUCCESS = "Moved"
        const val TASK_STATUS_UPDATE_ERROR = "Fail"
    }

}


