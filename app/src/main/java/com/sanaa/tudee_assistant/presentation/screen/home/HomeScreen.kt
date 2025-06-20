package com.sanaa.tudee_assistant.presentation.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import com.sanaa.tudee_assistant.presentation.design_system.component.AppBar
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.design_system.component.DarkModeThemeSwitchButton
import com.sanaa.tudee_assistant.presentation.design_system.component.EmptyScreen
import com.sanaa.tudee_assistant.presentation.design_system.component.Slider
import com.sanaa.tudee_assistant.presentation.design_system.component.TaskCountByStatusCard
import com.sanaa.tudee_assistant.presentation.design_system.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.add_edit_screen.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.screen.category.CategoryUiModel
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.formatDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    isDark: Boolean,
    onChangeTheme: () -> Unit,
    viewModel: HomeScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    HomeScreenContent(
        isDark = isDark,
        state = state,
        onChangeTheme = onChangeTheme,
        viewModel::onAddTask,
        viewModel::onTaskClick,
        viewModel::onOpenCategory,
        viewModel::onAddTaskSuccess,
        viewModel::onAddTaskHasError,
    )
}

@Composable
fun HomeScreenContent(
    isDark: Boolean,
    state: HomeScreenUiState,
    onChangeTheme: () -> Unit,
    onAddTask: () -> Unit,
    onTaskClick: (TaskUiState) -> Unit,
    onOpenCategory: (TaskUiStatus) -> Unit,
    onAddTaskSuccess: () -> Unit,
    onAddTaskHasError: (String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    var showEditTaskBottomSheet by remember { mutableStateOf(false) }
    var isScrolled by remember { mutableStateOf(false) }

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
                        isDark,
                        onCheckedChange = {
                            onChangeTheme()
                        })
                }
            )

            if (isScrolled) {
                Line()
            }

            CategoryList(scrollState, state, onOpenCategory, onTaskClick)
        }


        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 10.dp, horizontal = Theme.dimension.regular),
            iconRes = R.drawable.note_add,
        ) {
            showEditTaskBottomSheet = true
            onAddTask()
        }

        if (showEditTaskBottomSheet) {
            AddEditTaskScreen(
                isEditMode = false,
                onDismiss = {
                    showEditTaskBottomSheet = false
                },
                onSuccess = {
                    showEditTaskBottomSheet = false
                    onAddTaskSuccess()
                },
                onError = { errorMessage ->
                    onAddTaskHasError(errorMessage)
                }
            )
        }
    }
}

@Composable
private fun Line() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Theme.color.primary)
            .background(Theme.color.stroke)
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun CategoryList(
    scrollState: LazyListState,
    state: HomeScreenUiState,
    onOpenCategory: (TaskUiStatus) -> Unit,
    onTaskClick: (TaskUiState) -> Unit,
) {
    LazyColumn(
        state = scrollState,
        modifier = Modifier,
        contentPadding = PaddingValues(bottom = Theme.dimension.regular)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .background(Theme.color.primary)
                )

                InfoCard(state)
            }
        }

        item {
            AnimatedVisibility(
                state.tasks.isEmpty(),
                exit = shrinkVertically(
                    shrinkTowards = Alignment.Top,
                    animationSpec = tween(1000)
                ) + fadeOut(tween(500))
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EmptyScreen()
                }
            }
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.DONE }) {
                Title(
                    text = stringResource(R.string.done_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.DONE }.size,
                    onOpenClick = { onOpenCategory(TaskUiStatus.DONE) }
                )
            }
        }

        item {
            CategoryList(
                items = state.tasks.filter { it.status == TaskUiStatus.DONE },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.IN_PROGRESS }) {
                Title(
                    text = stringResource(R.string.in_progress_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                    onOpenClick = { onOpenCategory(TaskUiStatus.IN_PROGRESS) }
                )
            }
        }

        item {
            CategoryList(
                items = state.tasks.filter { it.status == TaskUiStatus.IN_PROGRESS },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }

        stickyHeader {
            if (state.tasks.any { it.status == TaskUiStatus.TODO }) {
                Title(
                    text = stringResource(R.string.todo_task_status),
                    tasksCount = state.tasks.filter { it.status == TaskUiStatus.TODO }.size,
                    onOpenClick = { onOpenCategory(TaskUiStatus.TODO) }
                )
            }
        }

        item {
            CategoryList(
                items = state.tasks.filter { it.status == TaskUiStatus.TODO },
                categories = state.categories,
                onClick = { onTaskClick(it) }
            )
        }
    }
}

@Composable
private fun CategoryList(
    items: List<TaskUiState>,
    categories: List<CategoryUiModel>,
    onClick: (TaskUiState) -> Unit,
) {
    LazyHorizontalGrid(
        modifier = Modifier.height(if (items.isNotEmpty()) 222.dp + Theme.dimension.extraLarge else 0.dp),
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = Theme.dimension.medium,
            end = Theme.dimension.medium,
            bottom = Theme.dimension.medium
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
    ) {
        items(items = items) { task ->
            val categoryImagePath = categories.first { it.id == task.categoryId }.imagePath
            CategoryTaskCard(
                modifier = Modifier.width(320.dp),
                task = task,
                categoryImagePath = categoryImagePath,
                onClick = { onClick(it) })
        }
    }
}

@Composable
private fun InfoCard(state: HomeScreenUiState) {
    Column(
        modifier = Modifier
            .padding(
                top = Theme.dimension.large,
                bottom = Theme.dimension.medium,
                start = Theme.dimension.medium,
                end = Theme.dimension.medium
            )
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .background(Theme.color.surfaceHigh)
            .padding(top = Theme.dimension.small, bottom = Theme.dimension.regular)
    ) {
        TopDate(state)

        Slider(modifier = Modifier.padding(start = 12.dp), tasks = state.tasks)

        Text(
            modifier = Modifier.padding(
                start = Theme.dimension.regular,
                top = Theme.dimension.small
            ),
            text = stringResource(R.string.overview),
            color = Theme.color.title,
            style = Theme.textStyle.title.large
        )

        Row(
            modifier = Modifier.padding(
                top = Theme.dimension.small,
                start = Theme.dimension.regular,
                end = Theme.dimension.regular
            ),
            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
        ) {
            TaskCountByStatusCard(
                count = state.tasks.filter { it.status == TaskUiStatus.DONE }.size,
                taskUiStatus = TaskUiStatus.DONE,
            )
            TaskCountByStatusCard(
                count = state.tasks.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                taskUiStatus = TaskUiStatus.IN_PROGRESS,
            )
            TaskCountByStatusCard(
                count = state.tasks.filter { it.status == TaskUiStatus.TODO }.size,
                taskUiStatus = TaskUiStatus.TODO,
            )
        }
    }
}

@Composable
private fun Title(
    text: String,
    tasksCount: Int,
    onOpenClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(Theme.color.surface)
            .padding(horizontal = Theme.dimension.medium, vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            color = Theme.color.title,
            style = Theme.textStyle.title.large
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .clickable { onOpenClick() }
                .background(Theme.color.surfaceHigh)
                .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = tasksCount.toString(),
                color = Theme.color.body,
                style = Theme.textStyle.label.small
            )

            Icon(
                painter = painterResource(R.drawable.nex_arrow),
                tint = Theme.color.body,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun TopDate(state: HomeScreenUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(Theme.dimension.medium),
            painter = painterResource(id = R.drawable.calendar_favorite_01),
            contentDescription = null,
            tint = Theme.color.body
        )

        Text(
            modifier = Modifier.padding(start = Theme.dimension.small),
            text = "${stringResource(R.string.today)}, " + state.dayDate.formatDateTime(context = LocalContext.current),
            color = Theme.color.body,
            style = Theme.textStyle.label.medium
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    val dayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var isDark by remember { mutableStateOf(false) }
    TudeeTheme(isDark = isDark) {
        val list = DataProvider.getTasksSample()

        HomeScreenContent(
            isDark,
            HomeScreenUiState(
                dayDate = dayDate,
                taskCounts = listOf(
                    Pair(list.filter { it.status == TaskUiStatus.DONE }.size, TaskUiStatus.DONE),
                    Pair(
                        list.filter { it.status == TaskUiStatus.IN_PROGRESS }.size,
                        TaskUiStatus.IN_PROGRESS
                    ),
                    Pair(list.filter { it.status == TaskUiStatus.TODO }.size, TaskUiStatus.TODO),
                ),
                tasks = list
            ),
            onChangeTheme = { isDark = !isDark }, {}, {}, {}, {}, {}
        )
    }
}