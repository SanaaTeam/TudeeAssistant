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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.component.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.component.TaskStatusTabs
import com.sanaa.tudee_assistant.presentation.component.TextAppBar
import com.sanaa.tudee_assistant.presentation.component.TudeeScaffold
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.DeleteComponent
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.AddEditTaskScreen
import com.sanaa.tudee_assistant.presentation.component.bottomSheet.task.taskDetailsBottomSheet.TaskDetailsComponent
import com.sanaa.tudee_assistant.presentation.designSystem.component.DayItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.navigation.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.shared.LocalSnackBarState
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.getShortMonthName
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.util.Locale

@Composable
fun TasksScreen(
    screenRoute: TasksScreenRoute,
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = koinViewModel<TaskViewModel>(parameters = { parametersOf(screenRoute.taskStatus) }),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
    var showEditTaskBottomSheet by rememberSaveable { mutableStateOf(false) }
    var taskToEdit by rememberSaveable { mutableStateOf<TaskUiState?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showAddTaskBottomSheet by rememberSaveable { mutableStateOf(false) }
    var daysInMonth by remember {
        mutableStateOf(
            DateFormater.getLocalDatesInMonth(
                state.selectedDate.year, state.selectedDate.monthNumber
            )
        )
    }

    val snackBarState = LocalSnackBarState.current
    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            snackBarState.value = state.snackBarState
            interactionListener.onHideSnakeBar()
        }
    }
    TudeeScaffold(
        floatingActionButton = {
            FloatingActionButton(
                enabled = true,
                modifier = Modifier,
                onClick = {
                    showAddTaskBottomSheet = true
                },
                iconRes = R.drawable.note_add
            )
        },
        topBar = {
            TextAppBar(
                modifier = Modifier
                    .background(color = Theme.color.surfaceHigh)
                    .statusBarsPadding(),
                title = stringResource(R.string.tasks)
            )
        }
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Theme.color.surfaceHigh)
            ) {
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
                            .clickable {
                                val nextMonth = state.selectedDate.minus(1, DateTimeUnit.MONTH)
                                interactionListener.onDateSelected(nextMonth)
                                daysInMonth = (DateFormater.getLocalDatesInMonth(
                                    nextMonth.year, nextMonth.monthNumber
                                ))
                            }
                            .border(1.dp, Theme.color.stroke, CircleShape)
                            .padding(6.dp), contentAlignment = Alignment.Center
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
                                .clickable(
                                    interactionSource = null,
                                    indication = null,
                                    onClick = { showDialog = true },
                                )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                val previousMonth = state.selectedDate.plus(1, DateTimeUnit.MONTH)
                                interactionListener.onDateSelected(previousMonth)
                                daysInMonth = (DateFormater.getLocalDatesInMonth(
                                    previousMonth.year, previousMonth.monthNumber
                                ))
                            }
                            .border(1.dp, Theme.color.stroke, CircleShape)
                            .padding(6.dp), contentAlignment = Alignment.Center
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
                val isArabic = Locale.getDefault().language == "arabic"
                LazyRow(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp),
                    reverseLayout = isArabic
                ) {
                    itemsIndexed(daysInMonth) { index, date ->
                        DayItem(
                            dayDate = date, isSelected = date == state.selectedDate, onClick = {
                                interactionListener.onDateSelected(date)
                            })
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

                TaskStatusTabs(
                    state,
                    interactionListener::onTaskSwipeToDelete,
                    interactionListener::onTaskClicked,
                    interactionListener::onTapClick
                )
                if (state.selectedTask != null && state.showTaskDetailsBottomSheet)
                    TaskDetailsComponent(
                        selectedTaskId = state.selectedTask.id,
                        onDismiss = { interactionListener.onDismissTaskDetails(false) },
                        onEditClick = { task ->
                            interactionListener.onDismissTaskDetails(false)
                            taskToEdit = task
                            showEditTaskBottomSheet = true
                        },
                        onMoveStatusSuccess = { interactionListener.handleOnMoveToStatusSuccess() },
                        onMoveStatusFail = { interactionListener.handleOnMoveToStatusFail() },
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
                        initialDate = state.selectedDate,
                        onDismiss = { showAddTaskBottomSheet = false },
                        onSuccess = {
                            showAddTaskBottomSheet = false
                            interactionListener.onAddTaskSuccess()
                        },
                        onError = { errorMessage ->
                            coroutineScope.launch {
                                snackBarHostState.showSnackbar(
                                    message = errorMessage, withDismissAction = true
                                )
                            }
                        })
                }

                if (showEditTaskBottomSheet && taskToEdit != null) {
                    AddEditTaskScreen(isEditMode = true, taskToEdit = taskToEdit, onDismiss = {
                        showEditTaskBottomSheet = false
                        taskToEdit = null
                    }, onSuccess = {
                        showEditTaskBottomSheet = false
                        taskToEdit = null
                        interactionListener.onEditTaskSuccess()
                    }, onError = { errorMessage ->
                        coroutineScope.launch {
                            snackBarHostState.showSnackbar(
                                message = errorMessage, withDismissAction = true
                            )
                        }
                    })
                }
            }
        }
    }
}
