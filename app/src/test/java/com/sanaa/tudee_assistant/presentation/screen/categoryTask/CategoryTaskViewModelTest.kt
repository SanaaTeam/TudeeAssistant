package com.sanaa.tudee_assistant.presentation.screen.categoryTask

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.domain.entity.Category
import com.sanaa.tudee_assistant.domain.entity.Task
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

        coEvery { categoryService.getCategoryById(categoryId) } returns fakeCategory
        coEvery { taskService.getTasksByCategoryId(categoryId) } returns flowOf(fakeTasks)

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
    fun `should load category and tasks when ViewModel is initialized`() = runTest {
        val state = viewModel.state.value

        assertThat(state.currentCategory.id).isEqualTo(categoryId)
        assertThat(state.todoTasks).hasSize(1)
        assertThat(state.inProgressTasks).hasSize(1)
        assertThat(state.doneTasks).hasSize(1)
        assertThat(state.isLoading).isFalse()
    }

    @Test
    fun `should show delete bottom sheet when delete is clicked`() {
        viewModel.onDeleteClicked()
        val state = viewModel.state.value

        assertThat(state.showDeleteCategoryBottomSheet).isTrue()
        assertThat(state.showEditCategoryBottomSheet).isFalse()
    }

    @Test
    fun `should hide delete bottom sheet and show edit bottom sheet when delete is dismissed`() {
        viewModel.onDeleteClicked()
        viewModel.onDeleteDismiss()
        val state = viewModel.state.value

        assertThat(state.showDeleteCategoryBottomSheet).isFalse()
        assertThat(state.showEditCategoryBottomSheet).isTrue()
    }

    @Test
    fun `should show edit bottom sheet with current category when edit is clicked`() {
        viewModel.onEditClicked()
        val state = viewModel.state.value

        assertThat(state.showEditCategoryBottomSheet).isTrue()
        assertThat(state.editCategory).isEqualTo(state.currentCategory)
    }

    @Test
    fun `should hide edit bottom sheet when edit is dismissed`() {
        viewModel.onEditClicked()
        viewModel.onEditDismissClicked()
        val state = viewModel.state.value

        assertThat(state.showEditCategoryBottomSheet).isFalse()
    }

    @Test
    fun `should delete category and hide delete bottom sheet when confirm delete is clicked`() = runTest {
        coEvery { categoryService.deleteCategoryById(categoryId) } returns Unit
        coEvery { taskService.deleteTaskByCategoryId(categoryId) } returns Unit

        viewModel.onConfirmDeleteClicked()
        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo(DELETE_SUCCESS)
        assertThat(state.showDeleteCategoryBottomSheet).isFalse()
    }

    @Test
    fun `should update selected task status when status is changed`() {
        viewModel.onStatusChanged(0)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.IN_PROGRESS)

        viewModel.onStatusChanged(1)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.TODO)

        viewModel.onStatusChanged(2)
        assertThat(viewModel.state.value.currentSelectedTaskStatus).isEqualTo(TaskUiStatus.DONE)
    }

    @Test
    fun `should update category name when title is changed`() {
        val newName = "New Category Name"
        viewModel.onTitleChange(newName)
        val state = viewModel.state.value

        assertThat(state.editCategory.name).isEqualTo(newName)
    }

    @Test
    fun `should show task details bottom sheet when task is clicked`() {
        val task = fakeUiTasks[0]
        viewModel.onTaskClicked(task)
        val state = viewModel.state.value

        assertThat(state.selectedTask).isEqualTo(task)
        assertThat(state.showTaskDetailsBottomSheet).isTrue()
    }

    @Test
    fun `should show edit task bottom sheet when task edit is clicked`() {
        val task = fakeUiTasks[0]
        viewModel.onTaskClicked(task)
        viewModel.onTaskEditClicked(task)
        val state = viewModel.state.value

        assertThat(state.showEditTaskBottomSheet).isTrue()
    }

    @Test
    fun `should hide edit task bottom sheet and clear selected task when dismiss is clicked`() {
        viewModel.onTaskEditClicked(fakeUiTasks[0])
        viewModel.onTaskEditDismiss()
        val state = viewModel.state.value

        assertThat(state.showEditTaskBottomSheet).isFalse()
        assertThat(state.selectedTask).isNull()
    }

    @Test
    fun `should show task update success message when edit succeeds`() {
        viewModel.onTaskEditSuccess()
        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo(TASK_UPDATE_SUCCESS)
        assertThat(state.showEditTaskBottomSheet).isFalse()
    }

    @Test
    fun `should show status update message when task status move succeeds`() {
        viewModel.onMoveStatusSuccess()
        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEqualTo(STATUS_UPDATE_SUCCESS)
        assertThat(state.showTaskDetailsBottomSheet).isFalse()
    }

    @Test
    fun `should clear snackbar message when snackbar is hidden`() {
        viewModel.onTaskEditSuccess()
        viewModel.onHideSnackBar()
        val state = viewModel.state.value

        assertThat(state.snackBarState.message).isEmpty()
    }

    @Test
    fun `should hide task details bottom sheet and clear selected task when dismissed`() {
        viewModel.onTaskClicked(fakeUiTasks[0])
        viewModel.onTaskDetailsDismiss()
        val state = viewModel.state.value

        assertThat(state.showTaskDetailsBottomSheet).isFalse()
        assertThat(state.selectedTask).isNull()
    }

    @Test
    fun `should return true from isValidForm when name is valid and changed`() {
        viewModel.onEditClicked()
        viewModel.onTitleChange("New Name")

        assertThat(viewModel.isValidForm()).isTrue()
    }

    @Test
    fun `should return false from isValidForm when name is too short`() {
        viewModel.onEditClicked()
        viewModel.onTitleChange("A")

        assertThat(viewModel.isValidForm()).isFalse()
    }

    // Fake data
    private val fakeCategory = Category(
        id = categoryId,
        name = "Test Category",
        imagePath = "path/to/image.jpg",
        isDefault = false
    )

    private val fakeTasks = listOf(
        Task(1, "Todo Task", "Description", Task.TaskStatus.TODO, LocalDate(2025, 6, 26), Task.TaskPriority.LOW, categoryId, LocalDateTime(2025, 6, 1, 12, 0)),
        Task(2, "In Progress Task", "Description", Task.TaskStatus.IN_PROGRESS, LocalDate(2025, 6, 26), Task.TaskPriority.MEDIUM, categoryId, LocalDateTime(2025, 6, 1, 12, 0)),
        Task(3, "Done Task", "Description", Task.TaskStatus.DONE, LocalDate(2025, 6, 26), Task.TaskPriority.HIGH, categoryId, LocalDateTime(2025, 6, 1, 12, 0))
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
