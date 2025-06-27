package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.component.TudeeScaffold
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.designSystem.component.AppBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.DarkModeThemeSwitchButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.CategoryList
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.Line
import com.sanaa.tudee_assistant.presentation.shared.LocalSnackBarState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = state,
        interactionsListener = viewModel,
    )
}

@Composable
private fun HomeScreenContent(
    state: HomeScreenUiState,
    interactionsListener: HomeScreenInteractionsListener,
) {
    val scrollState = rememberLazyListState()
    var isScrolled by remember { mutableStateOf(false) }

    val snackBarState = LocalSnackBarState.current

    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            snackBarState.value = state.snackBarState
            interactionsListener.onHideSnackBar()
        }
    }
    LaunchedEffect(scrollState) {
        snapshotFlow {
            Pair(scrollState.firstVisibleItemIndex, scrollState.firstVisibleItemScrollOffset)
        }.collect { (index, offset) ->
            isScrolled = if (index == 0) {
                offset > 55.dp.value
            } else {
                true
            }
        }
    }
    TudeeScaffold(
        contentBackground = Theme.color.surface,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                iconRes = R.drawable.note_add,
            )
            {
                interactionsListener.onShowAddTaskSheet()
            }
        },
        topBar = {
            AppBar(
                tailComponent = {
                    DarkModeThemeSwitchButton(
                        state.isDarkTheme,
                        800,
                        onCheckedChange = { interactionsListener.onToggleColorTheme() }
                    )
                },
                modifier = Modifier
                    .background(Theme.color.primary)
                    .statusBarsPadding()
            )
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface)
            ) {
                if (isScrolled) {
                    Line()
                }
                CategoryList(
                    scrollState,
                    state,
                    onTaskClick = { interactionsListener.onTaskClick(it) },
                    onNavigateToTaskScreen = { status ->
                        interactionsListener.onNavigateToTaskScreen(status)
                    }
                )
            }




            if (state.showAddTaskSheet) {
                AddEditTaskScreen(
                    isEditMode = false,
                    taskToEdit = null,
                    initialDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    onDismiss = { interactionsListener.onHideAddTaskSheet() },
                    onSuccess = { interactionsListener.onAddTaskSuccess() },
                    onError = { errorMessage -> interactionsListener.onAddTaskError(errorMessage) }
                )
            }
            if (state.showEditTaskSheet) {
                AddEditTaskScreen(
                    isEditMode = true,
                    taskToEdit = state.taskToEdit,
                    initialDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).date,
                    onDismiss = { interactionsListener.onHideEditTaskSheet() },
                    onSuccess = { interactionsListener.onEditTaskSuccess() },
                    onError = { errorMessage -> interactionsListener.onEditTaskError(errorMessage) }
                )


            }
            if (state.selectedTask != null && state.showTaskDetailsBottomSheet) {
                TaskDetailsComponent(
                    selectedTaskId = state.selectedTask.id,
                    onDismiss = { interactionsListener.onShowTaskDetails(false) },
                    onEditClick = { taskToEdit ->
                        interactionsListener.onShowEditTaskSheet(
                            taskToEdit
                        )
                    },
                    onMoveStatusSuccess = { interactionsListener.onMoveStatusSuccess() },
                    onMoveStatusFail = { interactionsListener.onMoveStatusFail() }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    val dayDate = DateUtil.today
    var isDark by remember { mutableStateOf(false) }
    TudeeTheme(isDark = isDark) {
        val list = DataProvider.getTasksSample()

        val previewActions = object : HomeScreenInteractionsListener {
            override fun onToggleColorTheme() {
                isDark = isDark.not()
            }

            override fun onAddTaskSuccess() {}
            override fun onAddTaskError(errorMessage: String) {}
            override fun onNavigateToTaskScreen(status: TaskUiStatus) {}
            override fun onShowEditTaskSheet(taskToEdit: TaskUiState) {}
            override fun onHideEditTaskSheet() {}
            override fun onEditTaskSuccess() {}
            override fun onEditTaskError(errorMessage: String) {}
            override fun onTaskClick(taskUiState: TaskUiState) {}
            override fun onShowTaskDetails(show: Boolean) {}
            override fun onShowAddTaskSheet() {}
            override fun onHideAddTaskSheet() {}
            override fun onMoveStatusSuccess() {}
            override fun onMoveStatusFail() {}
            override fun onHideSnackBar() {}
        }

        HomeScreenContent(
            state = HomeScreenUiState(
                isDarkTheme = isDark,
                todayDate = dayDate,
                taskCounts = listOf(
                    Pair(
                        list.filter { it.status == TaskUiStatus.DONE }.size,
                        TaskUiStatus.DONE
                    ),
                    Pair(
                        list.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                        TaskUiStatus.IN_PROGRESS
                    ),
                    Pair(
                        list.filter { it.status == TaskUiStatus.TODO }.size,
                        TaskUiStatus.TODO
                    ),
                ),
                tasks = list,
                selectedTask = TaskUiState()
            ),
            interactionsListener = previewActions
        )
    }
}