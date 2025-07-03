package com.sanaa.tudee_assistant.presentation.screen.home

import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.CategoryService
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.domain.service.StringProvider
import com.sanaa.tudee_assistant.domain.service.TaskService
import com.sanaa.tudee_assistant.presentation.base.BaseViewModel
import com.sanaa.tudee_assistant.presentation.model.SliderUiState
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.model.TudeeUiStatus
import com.sanaa.tudee_assistant.presentation.model.mapper.toDomain
import com.sanaa.tudee_assistant.presentation.model.mapper.toState
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

class HomeScreenViewModel(
    private val preferencesManager: PreferencesManager,
    private val taskService: TaskService,
    private val categoryService: CategoryService,
    private val stringProvider: StringProvider,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<HomeScreenUiState>(HomeScreenUiState(), dispatcher),
    HomeScreenInteractionsListener {
    init {
        loadScreen()
        getTasks()
    }

    private fun loadScreen() {
        tryToExecute(
            callee = {
                preferencesManager.isDarkTheme.collect { isDarkTheme ->
                    updateState { it.copy(isDarkTheme = isDarkTheme) }
                }
            },
            onError = ::showErrorMessage
        )
    }

    private fun getTasks() {
        tryToExecute(
            callee = {
                val today = DateUtil.today.date
                taskService.getTasksByDueDate(today).collectLatest { tasks ->
                    updateState { state -> state.copy(tasks = tasks.toState()) }
                    updateSliderUiState(tasks.toState())
                    categoryService.getCategories().collect { categories ->
                        updateState { state ->
                            state.copy(categories = categories.toState(tasks.size))
                        }
                    }
                }
            },
            onError = ::showErrorMessage
        )
    }

    override fun onToggleColorTheme() {
        tryToExecute(
            callee = {
                preferencesManager.setDarkTheme(state.value.isDarkTheme.not())
            },
            onError = ::showErrorMessage
        )
    }

    override fun onAddTaskSuccess() {
        getTasks()
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskAddedSuccess)
            )
        }
    }

    override fun onAddTaskError(errorMessage: String) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(errorMessage)
            )
        }
    }

    override fun onNavigateToTaskScreen(status: TaskUiStatus) {
        tryToExecute(
            callee = {
                preferencesManager.changeTaskStatus(status.toDomain())
            }
        )
    }

    override fun onShowEditTaskSheet(taskToEdit: TaskUiState) {
        updateState {
            it.copy(
                showEditTaskSheet = true,
                taskToEdit = taskToEdit,
                selectedTask = null
            )
        }
    }

    override fun onHideEditTaskSheet() {
        updateState { it.copy(showEditTaskSheet = false, taskToEdit = null) }
    }

    override fun onEditTaskSuccess() {
        getTasks()
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskUpdateSuccess)
            )
        }
    }

    override fun onEditTaskError(errorMessage: String) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(errorMessage)
            )
        }
    }

    override fun onHideSnackBar() {
        updateState {
            it.copy(snackBarState = SnackBarState.hide())
        }
    }

    override fun onTaskClick(taskUiState: TaskUiState) {
        updateState { it.copy(selectedTask = taskUiState, showTaskDetailsBottomSheet = true) }
    }

    override fun onShowTaskDetails(show: Boolean) {
        updateState { it.copy(selectedTask = null) }
        updateState { it.copy(showTaskDetailsBottomSheet = show) }
    }

    override fun onShowAddTaskSheet() {
        updateState { it.copy(showAddTaskSheet = true) }
    }

    override fun onHideAddTaskSheet() {
        updateState { it.copy(showAddTaskSheet = false) }
    }

    override fun onMoveStatusSuccess() {
        getTasks()
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateSuccess),
                showTaskDetailsBottomSheet = false,
            )
        }
    }

    override fun onMoveStatusFail() {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getInstance(stringProvider.taskStatusUpdateError)
            )
        }
    }

    private fun showErrorMessage(exception: Exception) {
        updateState {
            it.copy(
                snackBarState = SnackBarState.getErrorInstance(
                    exception.message ?: stringProvider.unknownError
                )
            )
        }
    }
    private fun updateSliderUiState(tasks: List<TaskUiState>) {
        val status = getSlideStatus(tasks)

        val title = when (status) {
            TudeeUiStatus.GOOD -> stringProvider.goodStatusMessageTitle
            TudeeUiStatus.OKAY -> stringProvider.okayStatusMessageTitle
            TudeeUiStatus.POOR -> stringProvider.poorStatusMessageTitle
            TudeeUiStatus.BAD -> stringProvider.badStatusMessageTitle
        }

        val message = when (status) {
            TudeeUiStatus.GOOD -> stringProvider.goodStatusMessage
            TudeeUiStatus.OKAY -> stringProvider.formattedOkayStatusMessage(
                done = tasks.count { it.status == TaskUiStatus.DONE },
                total = tasks.size
            )


            TudeeUiStatus.POOR -> stringProvider.poorStatusMessage
            TudeeUiStatus.BAD -> stringProvider.badStatusMessage
        }

        val robotImageRes = when (status) {
            TudeeUiStatus.OKAY, TudeeUiStatus.POOR -> R.drawable.robot1
            TudeeUiStatus.GOOD -> R.drawable.robot2
            TudeeUiStatus.BAD -> R.drawable.robot3
        }

        val sliderUiState = SliderUiState(
            title = title,
            message = message,
            robotImageRes = robotImageRes,
            status = status
        )

        updateState { it.copy(sliderUiState = sliderUiState) }
    }
    private fun getSlideStatus(tasks: List<TaskUiState>): TudeeUiStatus {
        val allTasksDone = tasks.all { it.status == TaskUiStatus.DONE }
        val allTasksNotFinished = tasks.none { it.status == TaskUiStatus.DONE }
        return when {
            tasks.isEmpty() -> TudeeUiStatus.POOR
            allTasksNotFinished -> TudeeUiStatus.BAD
            allTasksDone -> TudeeUiStatus.GOOD
            else -> TudeeUiStatus.OKAY
        }
    }
}