package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.designSystem.component.AppBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.DarkModeThemeSwitchButton
import com.sanaa.tudee_assistant.presentation.designSystem.component.EmptyScreen
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.Slider
import com.sanaa.tudee_assistant.presentation.designSystem.component.SnackBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskCountByStatusCard
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskItemCard
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.util.navigatePreservingState
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.CategoryList
import com.sanaa.tudee_assistant.presentation.screen.home.homeComponents.Line
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.formatDateTime
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
    var showAddTaskBottomSheet by remember { mutableStateOf(false) }
    var showEditTaskBottomSheet by remember { mutableStateOf(false) }
    var isScrolled by remember { mutableStateOf(false) }
    var taskToEdit by rememberSaveable { mutableStateOf<TaskUiState?>(null) }


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
            showAddTaskBottomSheet = true
        }

        if (showAddTaskBottomSheet) {
            AddEditTaskScreen(
                isEditMode = false,
                onDismiss = {
                    showAddTaskBottomSheet = false
                },
                onSuccess = {
                    showAddTaskBottomSheet = false
                    interactionsListener.onAddTaskSuccess()
                },
                onError = { errorMessage ->
                    interactionsListener.onAddTaskError(errorMessage)
                }
            )
        }
        if (showEditTaskBottomSheet) {
            AddEditTaskScreen(
                isEditMode = true,
                taskToEdit = taskToEdit,
                onDismiss = {
                    showEditTaskBottomSheet = false
                },
                onSuccess = {
                    showEditTaskBottomSheet = false
                    interactionsListener.onDismissTaskDetails()
                    interactionsListener.onEditTaskSuccess()
                },
                onError = { errorMessage ->
                    interactionsListener.onEditTaskError(errorMessage)
                }
            )
        }
        if (state.selectedTask != null) {
            TaskDetailsComponent(
                selectedTaskId = state.selectedTask.id,
                onDismiss = { interactionsListener.onDismissTaskDetails() },
                onEditClick = {
                    taskToEdit = it
                    showEditTaskBottomSheet = true
                },
                onMoveStatusSuccess = {
                    interactionsListener.onMoveStatusSuccess()
                },
                onMoveStatusFail = {
                    interactionsListener.onMoveStatusFail()
                }
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
            override fun onEditTaskSuccess() {}
            override fun onEditTaskError(errorMessage: String) {}
            override fun onTaskClick(taskUiState: TaskUiState) {}
            override fun onDismissTaskDetails() {}
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