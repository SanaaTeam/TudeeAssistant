package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.Task
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.ImageProcessor
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryTaskViewModelTest {

    private lateinit var viewModel: CategoryTaskViewModel
    private val categoryService: CategoryService = mockk(relaxed = true)
    private val taskService: TaskService = mockk(relaxed = true)
    private val imageProcessor: ImageProcessor = mockk(relaxed = true)
    private val stringProvider: StringProvider = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()
    private val categoryId = 1

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(dispatcher)

        // Mock initial data
        coEvery { categoryService.getCategoryById(categoryId) } returns fakeCategory
        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(fakeTasks)

        // Mock string resources
        every { stringProvider.deletedCategorySuccessfully } returns DELETE_SUCCESS
        every { stringProvider.categoryUpdateSuccessfully } returns UPDATE_SUCCESS
        every { stringProvider.taskUpdateSuccess } returns TASK_UPDATE_SUCCESS
        every { stringProvider.taskStatusUpdateSuccess } returns STATUS_UPDATE_SUCCESS
        every { stringProvider.deletingCategoryError } returns DELETE_ERROR


        viewModel = CategoryTaskViewModel(
            categoryService = categoryService,
            taskService = taskService,
            categoryId = categoryId,
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
    fun `initial state should load category and tasks`() = runTest {
        val state = viewModel.state.value

        assertThat(state.currentCategory.id).isEqualTo(categoryId)
        assertThat(state.todoTasks).hasSize(1)
        assertThat(state.inProgressTasks).hasSize(1)
        assertThat(state.doneTasks).hasSize(1)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `onDeleteClicked should show delete bottom sheet`() {
        viewModel.onDeleteClicked()

        val state = viewModel.state.value
        assertThat(state.showDeleteCategoryBottomSheet).isTrue()
        assertThat(state.showEditCategoryBottomSheet).isFalse()
    }

    @Test
    fun `onDeleteDismiss should hide delete bottom sheet`() {
        viewModel.onDeleteClicked()
        viewModel.onDeleteDismiss()

        val state = viewModel.state.value
        assertThat(state.showDeleteCategoryBottomSheet).isFalse()
        assertThat(state.showEditCategoryBottomSheet).isTrue()
    }

    @Test
    fun `onEditClicked should show edit bottom sheet with current category`() {
        viewModel.onEditClicked()

        val state = viewModel.state.value
        assertThat(state.showEditCategoryBottomSheet).isTrue()
        assertThat(state.editCategory).isEqualTo(state.currentCategory)
    }

    @Test
    fun `onEditDismissClicked should hide edit bottom sheet`() {
        viewModel.onEditClicked()
        viewModel.onEditDismissClicked()

        val state = viewModel.state.value
        assertThat(state.showEditCategoryBottomSheet).isFalse()
    }

    @Test
    fun `onConfirmDeleteClicked should delete category and navigate back`() = runTest {
        coEvery { categoryService.deleteCategoryById(categoryId) } returns Unit
        coEvery { taskService.deleteTaskByCategoryId(categoryId) } returns Unit

        viewModel.onConfirmDeleteClicked()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(DELETE_SUCCESS)
        assertThat(state.showDeleteCategoryBottomSheet).isFalse()
    }

    @Test
    fun `onStatusChanged should update selected status`() {
        viewModel.onStatusChanged(0)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.IN_PROGRESS)

        viewModel.onStatusChanged(1)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.TODO)

        viewModel.onStatusChanged(2)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.DONE)
    }


    @Test
    fun `onTitleChange should update edit category name`() {
        val newName = "New Category Name"
        viewModel.onTitleChange(newName)

        val state = viewModel.state.value
        assertThat(state.editCategory.name).isEqualTo(newName)
    }

    @Test
    fun `onTaskClicked should show task details bottom sheet`() {
        val task = fakeUiTasks[0]
        viewModel.onTaskClicked(task)

        val state = viewModel.state.value
        assertThat(state.selectedTask).isEqualTo(task)
        assertThat(state.showTaskDetailsBottomSheet).isTrue()
    }

    @Test
    fun `onTaskEditClicked should show edit task bottom sheet`() {
        val task = fakeUiTasks[0]
        viewModel.onTaskClicked(task)
        viewModel.onTaskEditClicked(task)

        val state = viewModel.state.value
        assertThat(state.showEditTaskBottomSheet).isTrue()
    }

    @Test
    fun `onTaskEditDismiss should hide edit task bottom sheet`() {
        viewModel.onTaskEditClicked(fakeUiTasks[0])
        viewModel.onTaskEditDismiss()

        val state = viewModel.state.value
        assertThat(state.showEditTaskBottomSheet).isFalse()
        assertThat(state.selectedTask).isNull()
    }

    @Test
    fun `onTaskEditSuccess should show success message`() {
        viewModel.onTaskEditSuccess()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(TASK_UPDATE_SUCCESS)
        assertThat(state.showEditTaskBottomSheet).isFalse()
    }

    @Test
    fun `onMoveStatusSuccess should show success message`() {
        viewModel.onMoveStatusSuccess()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEqualTo(STATUS_UPDATE_SUCCESS)
        assertThat(state.showTaskDetailsBottomSheet).isFalse()
    }

    @Test
    fun `onHideSnackBar should reset snackbar state`() {
        viewModel.onTaskEditSuccess()
        viewModel.onHideSnackBar()

        val state = viewModel.state.value
        assertThat(state.snackBarState.message).isEmpty()
    }

    @Test
    fun `onTaskDetailsDismiss should hide details bottom sheet`() {
        viewModel.onTaskClicked(fakeUiTasks[0])
        viewModel.onTaskDetailsDismiss()

        val state = viewModel.state.value
        assertThat(state.showTaskDetailsBottomSheet).isFalse()
        assertThat(state.selectedTask).isNull()
    }

    @Test
    fun `isValidForm should return true when name is valid and changes exist`() {
        viewModel.onEditClicked()
        viewModel.onTitleChange("New Name")

        assertThat(viewModel.isValidForm()).isTrue()
    }

    @Test
    fun `isValidForm should return false when name is invalid`() {
        viewModel.onEditClicked()
        viewModel.onTitleChange("A")

        assertThat(viewModel.isValidForm()).isFalse()
    }

    // Test data
    private val fakeCategory = Category(
        id = categoryId,
        name = "Test Category",
        imagePath = "path/to/image.jpg",
        isDefault = false
    )

    private val fakeTasks = listOf(
        Task(
            id = 1,
            title = "Todo Task",
            description = "Description",
            status = Task.TaskStatus.TODO,
            dueDate = LocalDate(2025, 6, 26),
            priority = Task.TaskPriority.LOW,
            categoryId = categoryId,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0)
        ),
        Task(
            id = 2,
            title = "In Progress Task",
            description = "Description",
            status = Task.TaskStatus.IN_PROGRESS,
            dueDate = LocalDate(2025, 6, 26),
            priority = Task.TaskPriority.MEDIUM,
            categoryId = categoryId,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0)
        ),
        Task(
            id = 3,
            title = "Done Task",
            description = "Description",
            status = Task.TaskStatus.DONE,
            dueDate = LocalDate(2025, 6, 26),
            priority = Task.TaskPriority.HIGH,
            categoryId = categoryId,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0)
        )
    )

    private val fakeUiTasks = listOf(
        TaskUiState(
            id = 1,
            categoryId = categoryId,
            title = "Todo Task",
            description = "Description",
            status = TaskUiStatus.TODO,
            createdAt = LocalDateTime(2025, 6, 1, 12, 0).toString()
        )
    )

    private companion object {
        const val DELETE_SUCCESS = "Category deleted successfully"
        const val UPDATE_SUCCESS = "Category updated successfully"
        const val TASK_UPDATE_SUCCESS = "Task updated successfully"
        const val STATUS_UPDATE_SUCCESS = "Status updated successfully"
        const val DELETE_ERROR = "Error deleting category"
    }
}