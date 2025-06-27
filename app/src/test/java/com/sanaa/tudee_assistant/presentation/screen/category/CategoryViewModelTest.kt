package com.sanaa.tudee_assistant.presentation.screen.category

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryViewModelTest {

    private val categoryService: CategoryService = mockk(relaxed = true)
    private val taskService: TaskService = mockk(relaxed = true)
    private val imageProcessor: ImageProcessor = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: CategoryViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        coEvery { categoryService.getCategories() } returns flowOf(emptyList())
        coEvery { taskService.getTaskCountsGroupedByCategoryId() } returns flowOf(emptyMap())
        every { stringProvider.categoryAddedSuccessfully } returns "Category Added"
        every { stringProvider.unknownError } returns "Unknown Error"

        viewModel = CategoryViewModel(
            categoryService = categoryService,
            taskService = taskService,
            imageProcessor = imageProcessor,
            stringProvider = stringProvider,
            dispatcher = dispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `isFormValid should return true when valid`() {
        viewModel.onCategoryTitleChange("Work")
        viewModel.onCategoryImageSelectedFromPath("file://image.png")
        assertThat(viewModel.isFormValid()).isTrue()
    }

    @Test
    fun `isFormValid should return false when title is blank`() {
        viewModel.onCategoryTitleChange("")
        viewModel.onCategoryImageSelectedFromPath("file://image.png")

        assertThat(viewModel.isFormValid()).isFalse()
    }

    @Test
    fun `onCategoryTitleChange should update title`() {
        viewModel.onCategoryTitleChange("Personal")

        assertThat(viewModel.state.value.newCategory.name).isEqualTo("Personal")
    }

    @Test
    fun `onCategoryImageSelected should update imagePath`() {
        val fakePath = "file://image.jpg"
        viewModel.onCategoryImageSelectedFromPath(fakePath)
        assertThat(viewModel.state.value.newCategory.imagePath).isEqualTo(fakePath)
    }

    @Test
    fun `onHideSnackBar should reset snackbar state`() {
        viewModel.onHideSnakeBar()
        assertThat(viewModel.state.value.snackBarState).isEqualTo(SnackBarState.hide())
    }
}