package com.sanaa.tudee_assistant.presentation.screen.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.DeleteComponent
import com.sanaa.tudee_assistant.presentation.composable.TaskStatusTabs
import com.sanaa.tudee_assistant.presentation.designSystem.component.DayItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.TudeeSnackBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.route.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.composable.bottomSheet.task.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.getShortMonthName
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun TasksScreen(
    screenRoute: TasksScreenRoute,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = koinViewModel<TaskViewModel>(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.onTaskStatusSelectedChange(screenRoute.taskStatus)
    }

    TasksScreenContent(
        state = state,
        interactionListener = viewModel,
        modifier = modifier,
    )
}


@Composable
fun TasksScreenContent(
    state: TasksScreenUiState,
    interactionListener: TaskInteractionListener,
    modifier: Modifier = Modifier,
) {

    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showEditTaskBottomSheet by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<TaskUiState?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showAddTaskBottomSheet by remember { mutableStateOf(false) }
    var daysInMonth by
    remember {
        mutableStateOf(
            DateFormater.getLocalDatesInMonth(
                state.selectedDate.year,
                state.selectedDate.monthNumber
            )
        )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.color.surfaceHigh),
        ) {
            Text(
                text = stringResource(R.string.tasks),
                style = Theme.textStyle.title.large,
                color = Theme.color.title,
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp)
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Theme.color.stroke, CircleShape)
                        .padding(6.dp)
                        .clickable {
                            val nextMonth = state.selectedDate.minus(1, DateTimeUnit.MONTH)
                            interactionListener.onDateSelected(nextMonth)
                            daysInMonth =
                                (DateFormater.getLocalDatesInMonth(
                                    nextMonth.year,
                                    nextMonth.monthNumber
                                ))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_left_arrow),
                        contentDescription = "back",
                        tint = Theme.color.body,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${state.selectedDate.getShortMonthName()}, ${state.selectedDate.year}",
                        color = Theme.color.body,
                        style = Theme.textStyle.label.medium
                    )
                    Icon(
                        painter = painterResource(R.drawable.icon_left_arrow),
                        contentDescription = "calender",
                        tint = Theme.color.body,
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(if (LocalLayoutDirection.current == LayoutDirection.Rtl) 90f else -90f)
                            .clickable {
                                showDialog = true
                            }
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(1.dp, Theme.color.stroke, CircleShape)
                        .padding(6.dp)
                        .clickable {
                            val previousMonth = state.selectedDate.plus(1, DateTimeUnit.MONTH)
                            interactionListener.onDateSelected(previousMonth)
                            daysInMonth =
                                (DateFormater.getLocalDatesInMonth(
                                    previousMonth.year,
                                    previousMonth.monthNumber
                                ))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_left_arrow),
                        contentDescription = "next",
                        tint = Theme.color.body,
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(180f)
                    )
                }

            }
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = state.selectedDate) {
                coroutineScope.launch {
                    listState.animateScrollToItem(state.selectedDate.dayOfMonth - 1)
                }
            }
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                itemsIndexed(daysInMonth) { index, date ->
                    DayItem(
                        dayDate = date,
                        isSelected = date == state.selectedDate,
                        onClick = {
                            interactionListener.onDateSelected(date)
                        }
                    )
                }
            }

            if (showDialog) {
                CustomDatePickerDialog(
                    onDateSelected = { selectedDateMillis: Long? ->
                        selectedDateMillis?.let {
                            val date = DateFormater.formatLongToDate(selectedDateMillis)
                            interactionListener.onDateSelected(date)
                            daysInMonth =
                                (DateFormater.getLocalDatesInMonth(date.year, date.monthNumber))
                        }
                    },
                    onDismiss = { showDialog = false },
                    initialSelectedDate = state.selectedDate
                )
            }
            TaskStatusTabs(state, interactionListener::onTaskSwipeToDelete, interactionListener::onTaskClicked)
            if (state.selectedTask != null && state.showTaskDetailsBottomSheet)
                TaskDetailsComponent(
                    task = state.selectedTask,
                    onDismiss = { interactionListener.onDismissTaskDetails(false) },
                    onEditClick = { task ->
                        interactionListener.onDismissTaskDetails(false)
                        taskToEdit = task
                        showEditTaskBottomSheet = true
                    },
                    onMoveToClicked = interactionListener::onMoveTaskToAnotherStatus,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            if (state.selectedTask != null && state.showDeleteTaskBottomSheet)
                DeleteComponent(
                    onDismiss = interactionListener::onDeleteDismiss,
                    onDeleteClicked = interactionListener::onDeleteTask,
                    title = stringResource(R.string.delete_task_title),
                )
            if (showAddTaskBottomSheet) {
                AddEditTaskScreen(
                    isEditMode = false,
                    taskToEdit = null,
                    onDismiss = { showAddTaskBottomSheet = false },
                    onSuccess = {
                        showAddTaskBottomSheet = false
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Task added successfully",
                                withDismissAction = true
                            )
                        }
                    },
                    onError = { errorMessage ->
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = errorMessage,
                                withDismissAction = true
                            )
                        }
                    }
                )
            }

            if (showEditTaskBottomSheet && taskToEdit != null) {
                AddEditTaskScreen(
                    isEditMode = true,
                    taskToEdit = taskToEdit,
                    onDismiss = {
                        showEditTaskBottomSheet = false
                        taskToEdit = null
                    },
                    onSuccess = {
                        showEditTaskBottomSheet = false
                        taskToEdit = null
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Task updated successfully",
                                withDismissAction = true
                            )
                        }
                    },
                    onError = { errorMessage ->
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = errorMessage,
                                withDismissAction = true
                            )
                        }
                    }
                )
            }
        }

        FloatingActionButton(
            enabled = true,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 12.dp),
            onClick = {
                coroutineScope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                }
                showAddTaskBottomSheet = true
            },
            iconRes = R.drawable.note_add
        )

        var successMessageText : String? = null
        state.successMessageStringId?.let {
            successMessageText = stringResource(state.successMessageStringId)
        }

        LaunchedEffect(state.successMessageStringId) {
            state.successMessageStringId?.let {
                snackBarHostState.showSnackbar(successMessageText!!)
                interactionListener.onShowSnackbar()
            }
        }
        var errorMessageText : String? = null
        state.errorMessageStringId?.let {
            successMessageText = stringResource(state.errorMessageStringId)
        }
        LaunchedEffect(state.successMessageStringId) {
            state.errorMessageStringId?.let {
                snackBarHostState.showSnackbar(errorMessageText!!)
                interactionListener.onShowSnackbar()
            }
        }

        TudeeSnackBar(
            snackBarHostState = snackBarHostState,
            isError = state.errorMessageStringId != null
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun TasksScreenPreview() {
//    var tasksState by remember {
//        mutableStateOf(
//            TasksScreenUiState(
//                currentDateTasks = DataProvider.getTasksSample(),
//                selectedStatusTab = TaskUiStatus.DONE,
//                selectedDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
//            )
//        )
//    }
//
//    val previewInteraction = object : TaskInteractionListener {
//        override fun onDateSelected(date: LocalDate) {
//            tasksState = tasksState.copy(
//                selectedDate = date,
//                currentDateTasks = DataProvider.getTasksSample() // Simulate fetch
//            )
//        }
//
//        override fun onTaskSwiped(task: TaskUiState) {
//            tasksState = tasksState.copy(
//                currentDateTasks = tasksState.currentDateTasks.filterNot { it == task }
//            )
//        }
//
//        override fun onTaskClicked(task: TaskUiState) {
//            tasksState = tasksState.copy(
//                selectedTask = task,
//                showTaskDetailsBottomSheet = true
//            )
//        }
//
//        override fun onDismissTaskDetailsDialog() {
//            tasksState = tasksState.copy(showTaskDetailsBottomSheet = false)
//        }
//
//        override fun onMoveTaskToAnotherStatus() = Unit
//        override fun onTaskDeleted() = Unit
//        override fun onTaskDeletedDismissed() = Unit
//        override fun onSnackbarShown() = Unit
//    }
//
//    TasksScreenContent(
//        state = tasksState,
//        interactionListener = previewInteraction
//    )
//}
