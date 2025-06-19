package com.sanaa.tudee_assistant.presentation.screen.taskScreen

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composable.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.design_system.component.DayItem
import com.sanaa.tudee_assistant.presentation.design_system.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.screen.taskDetalis.TaskViewDetails
import com.sanaa.tudee_assistant.presentation.state.TaskUiModel
import com.sanaa.tudee_assistant.presentation.utils.DataProvider
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.androidx.compose.koinViewModel


@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = koinViewModel<TaskViewModel>()
) {
    val state by viewModel.state.collectAsState()

    TasksScreenContent(
        state = state,
        onDateSelected = { date -> viewModel.onDueDateChange(date) },
        onTaskSwipe = { task ->
            viewModel.onTaskSwipeToDelete(task)
            false
        },
        onTaskClick = { task -> viewModel.onTaskClick(task) },
        onDismissTaskViewDetails = { viewModel.onShowTaskDetailsDialogChange(false) },
        onEditTaskViewDetails = { TODO() },
        onMoveToTaskViewDetails = { TODO() },
        modifier = modifier,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenContent(
    state: TasksScreenUiState,
    onTaskSwipe: (TaskUiModel) -> Boolean,
    onDateSelected: (LocalDate) -> Unit,
    onTaskClick: (TaskUiModel) -> Unit,
    onDismissTaskViewDetails: () -> Unit,
    onEditTaskViewDetails: (TaskUiModel) -> Unit,
    onMoveToTaskViewDetails: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var showDialog by remember { mutableStateOf(false) }

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
                text = "Tasks",
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
                        .border(1.dp, Theme.color.stroke, CircleShape)
                        .padding(6.dp),
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
                        text = "${state.selectedDate.month}, ${state.selectedDate.year}",
                        color = Theme.color.body,
                        style = Theme.textStyle.label.medium
                    )
                    Icon(
                        painter = painterResource(R.drawable.icon_left_arrow),
                        contentDescription = "next",
                        tint = Theme.color.body,
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(-90f)
                            .clickable {
                                showDialog = true
                            }
                    )
                }

                Box(
                    modifier = Modifier
                        .border(1.dp, Theme.color.stroke, CircleShape)
                        .padding(6.dp),
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

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(horizontal = 24.dp)
            ) {
                items(daysInMonth) { date ->
                    DayItem(
                        dayDate = date,
                        isSelected = date == state.selectedDate,
                        onClick = { onDateSelected(date) }
                    )
                }
            }

            if (showDialog) {
                CustomDatePickerDialog(
                    onDateSelected = { selectedDateMillis: Long? ->
                        selectedDateMillis?.let {
                            val date = DateFormater.formatLongToDate(selectedDateMillis)
                            onDateSelected(date)
                            daysInMonth =
                                (DateFormater.getLocalDatesInMonth(date.year, date.monthNumber))
                        }
                    },
                    onDismiss = { showDialog = false },
                    initialSelectedDate = state.selectedDate
                )
            }

            TaskStatusTabs(state, onTaskSwipe, onTaskClick)

            if (state.showTaskDetailsDialog && state.selectedTask != null) {

                TaskViewDetails(
                    state.selectedTask,
                    onDismissTaskViewDetails,
                    onEditClick = onEditTaskViewDetails,
                    onMoveToClicked = onMoveToTaskViewDetails,
//                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                )
            }
        }

        FloatingActionButton(enabled = true, modifier = Modifier.align(Alignment.BottomEnd).padding(end = 10.dp, bottom = 12.dp))
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
private fun TasksScreenPreview() {

    var tasksState by remember {
        mutableStateOf(
            TasksScreenUiState(
                currentDateTasks = DataProvider.getTasksSample(),
                isLoading = false,
                errorMessage = null,
                successMessage = null,
                showAddTaskDialog = false,
                showEditDialog = false,
                showTaskDetailsDialog = false,
                showDeleteDialog = false,
                selectedTaskUiStatus = TaskUiStatus.DONE,
                selectedTask = null,
                selectedDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date,
            )
        )
    }

    TasksScreenContent(
        tasksState,
        onDateSelected = { date ->
            tasksState = tasksState.copy(selectedDate = date)
            tasksState = tasksState.copy(currentDateTasks = DataProvider.getTasksSample())
        },
        onTaskSwipe = { task ->
            tasksState =
                tasksState.copy(currentDateTasks = tasksState.currentDateTasks.filterNot { item -> item == task })
            false
        },
        onTaskClick = { task ->
            tasksState = tasksState.copy(selectedTask = task, showTaskDetailsDialog = true)
        },
        onDismissTaskViewDetails = { tasksState = tasksState.copy(showTaskDetailsDialog = false) },
        onEditTaskViewDetails = {},
        onMoveToTaskViewDetails = {},
    )
}