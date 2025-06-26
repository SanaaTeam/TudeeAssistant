package com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.MainCoroutineRule
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.screen.tasks.addEditTask.AddEditTaskViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MainCoroutineRule::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AddEditTaskViewModelTest {

    private val taskService: TaskService = mockk(relaxed = true)
    private val categoryService: CategoryService = mockk(relaxed = true)
    private lateinit var viewModel: AddEditTaskViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = AddEditTaskViewModel(taskService, categoryService, testDispatcher)
        coEvery { categoryService.getCategories() } returns flowOf(emptyList())
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onTitleChange should update title when called`() = runTest {
        // Given
        val title = "New Task"

        // When
        viewModel.onTitleChange(title)

        // Then
        assertThat(viewModel.state.value.taskUiState.title).isEqualTo(title)
    }

    @Test
    fun `onDescriptionChange should update description when called`() = runTest {
        // Given
        val description = "Task Description"

        // When
        viewModel.onDescriptionChange(description)

        // Then
        assertThat(viewModel.state.value.taskUiState.description).isEqualTo(description)
    }

    @Test
    fun `onDateSelected should update due date when called`() = runTest {
        // Given
        val date = LocalDate(2025, 6, 1)

        // When
        viewModel.onDateSelected(date)

        // Then
        assertThat(viewModel.state.value.taskUiState.dueDate).isEqualTo("2025-06-01")
    }

    @Test
    fun `onPrioritySelected should update priority when called`() = runTest {
        // Given
        val priority = TaskUiPriority.HIGH

        // When
        viewModel.onPrioritySelected(priority)

        // Then
        assertThat(viewModel.state.value.taskUiState.priority).isEqualTo(priority)
    }


    @Test
    fun `onDatePickerShow should show date picker dialog when called`() = runTest {
        // When
        viewModel.onDatePickerShow()

        // Then
        assertThat(viewModel.showDatePickerDialog.value).isTrue()
    }

    @Test
    fun `onDatePickerDismiss should dismiss date picker dialog when called`() = runTest {
        // Given
        viewModel.onDatePickerShow()
        assertThat(viewModel.showDatePickerDialog.value).isTrue()

        // When
        viewModel.onDatePickerDismiss()

        // Then
        assertThat(viewModel.showDatePickerDialog.value).isFalse()
    }
    
    @Test
    fun `button is disabled when title is blank in add mode`() = runTest {
        // Given
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()

        viewModel.onTitleChange("")
        viewModel.onCategorySelected(category.toState(0))
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isFalse()
    }

    @Test
    fun `button is disabled when no category is selected in add mode`() = runTest {
        // Given
        coEvery { categoryService.getCategories() } returns flowOf(emptyList())
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()

        viewModel.onTitleChange("Task with no category")
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isFalse()
    }

    @Test
    fun `button is enabled when title and category are present in add mode`() = runTest {
        // Given
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()

        viewModel.onTitleChange("Valid Task")
        viewModel.onCategorySelected(category.toState(0))
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isTrue()
    }

    @Test
    fun `button is disabled in edit mode if no changes are made`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Original Title",
            description = "Original Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isFalse()
    }

    @Test
    fun `button is enabled in edit mode if title is changed`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Original Title",
            description = "Original Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()

        viewModel.onTitleChange("Changed Title")
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isTrue()
    }

    @Test
    fun `button is enabled in edit mode if description is changed`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Title",
            description = "Original Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()

        viewModel.onDescriptionChange("Changed Desc")
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isTrue()
    }



    @Test
    fun `button is enabled in edit mode if priority is changed`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Title",
            description = "Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()

        viewModel.onPrioritySelected(TaskUiPriority.HIGH)
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isTrue()
    }

    @Test
    fun `button is enabled in edit mode if category is changed`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Title",
            description = "Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val categories = listOf(
            Category(id = 1, name = "Work", imagePath = "", isDefault = true),
            Category(id = 2, name = "Personal", imagePath = "", isDefault = false)
        )
        coEvery { categoryService.getCategories() } returns flowOf(categories)
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()

        viewModel.onCategorySelected(categories[1].toState(0)) // Select a different category
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isButtonEnabled).isTrue()
    }

    @Test
    fun `initTaskState does not re-initialize if parameters are the same`() = runTest {
        // Given
        val taskToEdit = TaskUiState(
            id = 1,
            title = "Initial Task",
            description = "",
            dueDate = "",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))

        viewModel.initTaskState(isEditMode = true, taskToEdit = taskToEdit, initialDate = null)
        advanceUntilIdle()
        val initialHashCode = viewModel.state.value.hashCode()

        // When
        viewModel.initTaskState(isEditMode = true, taskToEdit = taskToEdit, initialDate = null)
        advanceUntilIdle()
        val afterCallHashCode = viewModel.state.value.hashCode()

        // Then
        assertThat(initialHashCode).isEqualTo(afterCallHashCode)
    }



    @Test
    fun `onPrimaryButtonClick does nothing when button is disabled`() = runTest {
        // Given
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()

        viewModel.onTitleChange("")
        viewModel.onCategorySelected(category.toState(0))
        advanceUntilIdle()

        assertThat(viewModel.state.value.isButtonEnabled).isFalse()

        // When
        viewModel.onPrimaryButtonClick()
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.isOperationSuccessful).isFalse()
        assertThat(viewModel.state.value.error).isNull()
    }



    @Test
    fun `addTask should set isOperationSuccessful true and call service when all fields valid`() = runTest {
        // Given
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        coEvery { taskService.addTask(any()) } returns Unit
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()
        viewModel.onTitleChange("Test Add Task")
        viewModel.onDescriptionChange("Description")
        viewModel.onDateSelected(LocalDate(2025, 6, 15))
        viewModel.onPrioritySelected(TaskUiPriority.HIGH)
        viewModel.onCategorySelected(category.toState(0))
        advanceUntilIdle()

        // When
        viewModel.onPrimaryButtonClick()
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isOperationSuccessful).isTrue()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNull()
        coVerify(exactly = 1) { taskService.addTask(any()) }
    }

    @Test
    fun `updateTask should set isOperationSuccessful true and call service when fields changed`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Original Title",
            description = "Original Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        coEvery { taskService.updateTask(any()) } returns Unit
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()
        viewModel.onTitleChange("Changed Title")
        advanceUntilIdle()

        // When
        viewModel.onPrimaryButtonClick()
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isOperationSuccessful).isTrue()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNull()
        coVerify(exactly = 1) { taskService.updateTask(any()) }
    }

    @Test
    fun `addTask should set error and not set isOperationSuccessful when service throws`() = runTest {
        // Given
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        coEvery { taskService.addTask(any()) } throws RuntimeException("Add error")
        viewModel.initTaskState(isEditMode = false, taskToEdit = null, initialDate = null)
        advanceUntilIdle()
        viewModel.onTitleChange("Test Add Task")
        viewModel.onDescriptionChange("Description")
        viewModel.onDateSelected(LocalDate(2025, 6, 15))
        viewModel.onPrioritySelected(TaskUiPriority.HIGH)
        viewModel.onCategorySelected(category.toState(0))
        advanceUntilIdle()

        // When
        viewModel.onPrimaryButtonClick()
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isOperationSuccessful).isFalse()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNotNull()
        coVerify(exactly = 1) { taskService.addTask(any()) }
    }

    @Test
    fun `updateTask should set error and not set isOperationSuccessful when service throws`() = runTest {
        // Given
        val originalTask = TaskUiState(
            id = 1,
            title = "Original Title",
            description = "Original Desc",
            dueDate = "2025-01-01",
            categoryId = 1,
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO
        )
        val category = Category(id = 1, name = "Work", imagePath = "", isDefault = true)
        coEvery { categoryService.getCategories() } returns flowOf(listOf(category))
        coEvery { taskService.updateTask(any()) } throws RuntimeException("Update error")
        viewModel.initTaskState(isEditMode = true, taskToEdit = originalTask, initialDate = null)
        advanceUntilIdle()
        viewModel.onTitleChange("Changed Title")
        advanceUntilIdle()

        // When
        viewModel.onPrimaryButtonClick()
        advanceUntilIdle()

        // Then
        assertThat(viewModel.state.value.isOperationSuccessful).isFalse()
        assertThat(viewModel.state.value.isLoading).isFalse()
        assertThat(viewModel.state.value.error).isNotNull()
        coVerify(exactly = 1) { taskService.updateTask(any()) }
    }

}