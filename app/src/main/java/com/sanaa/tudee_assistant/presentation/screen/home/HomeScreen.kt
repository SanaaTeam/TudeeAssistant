package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.designSystem.component.AppBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.DarkModeThemeSwitchButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.SnackBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.CategoryList
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.Line
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
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


    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            delay(3000)
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

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
        ) {
            AppBar(
                tailComponent = {
                    DarkModeThemeSwitchButton(
                        state.isDarkTheme,
                        onCheckedChange = { interactionsListener.onToggleColorTheme() }
                    )
                }
            )

            if (isScrolled) {
                Line()
            }
            CategoryList(
                scrollState,
                state,
                onTaskClick = { interactionsListener.onTaskClick(it) },
            )
        }


        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 10.dp, horizontal = Theme.dimension.regular),
            iconRes = R.drawable.note_add,
        ) {
            interactionsListener.onShowAddTaskSheet()
        }

        if (state.showAddTaskSheet) {
            AddEditTaskScreen(
                isEditMode = false,
                onDismiss = { interactionsListener.onHideAddTaskSheet() },
                onSuccess = { interactionsListener.onAddTaskSuccess() },
                onError = { errorMessage -> interactionsListener.onAddTaskError(errorMessage) }
            )
        }
        if (state.showEditTaskSheet) {
            AddEditTaskScreen(
                isEditMode = true,
                taskToEdit = state.taskToEdit,
                onDismiss = { interactionsListener.onHideEditTaskSheet() },
                onSuccess = { interactionsListener.onEditTaskSuccess() },
                onError = { errorMessage -> interactionsListener.onEditTaskError(errorMessage) }
            )
        }
        if (state.selectedTask != null) {
            TaskDetailsComponent(
                selectedTaskId = state.selectedTask.id,
                onDismiss = { interactionsListener.onDismissTaskDetails() },
                onEditClick = { taskToEdit -> interactionsListener.onShowEditTaskSheet(taskToEdit) },
                onMoveStatusSuccess = { interactionsListener.onMoveStatusSuccess() },
                onMoveStatusFail = { interactionsListener.onMoveStatusFail() }
            )
        }

        AnimatedVisibility(
            visible = state.snackBarState.isVisible,
            modifier = Modifier
                .align(Alignment.TopCenter),
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            SnackBar(state = state.snackBarState)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    val dayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var isDark by remember { mutableStateOf(false) }
    TudeeTheme(isDark = isDark) {
        val list = DataProvider.getTasksSample()

        val previewActions = object : HomeScreenInteractionsListener {
            override fun onToggleColorTheme() {
                isDark = isDark.not()
            }

            override fun onAddTaskSuccess() {}
            override fun onAddTaskError(errorMessage: String) {}
            override fun onShowEditTaskSheet(taskToEdit: TaskUiState) {}
            override fun onHideEditTaskSheet() {}
            override fun onEditTaskSuccess() {}
            override fun onEditTaskError(errorMessage: String) {}
            override fun onTaskClick(taskUiState: TaskUiState) {}
            override fun onDismissTaskDetails() {}
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