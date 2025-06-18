package com.sanaa.tudee_assistant.presentation.taskScreen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composables.CustomDatePickerDialog
import com.sanaa.tudee_assistant.presentation.design_system.component.DayItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TabItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTabs
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.utils.DateFormater
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TasksScreen(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit = {},
    onDaySelected: (LocalDate) -> Unit = {},
) {

    var showDialog by remember { mutableStateOf(false) }

    var daysInMonth by
    remember { mutableStateOf(DateFormater.getLocalDatesInMonth(selectedDate.year, selectedDate.monthNumber)) }


    val tabs = listOf(
        TabItem(
            label = stringResource(R.string.in_progress_task_status),
            count = 14
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("In Progress Content", color = Theme.color.title)
            }
        },
        TabItem(
            label = stringResource(R.string.todo_task_status),
            count = 0
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("To Do Content", color = Theme.color.title)
            }
        },

        TabItem(
            label = stringResource(R.string.done_task_status),
            count = 0
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.color.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("Done Content", color = Theme.color.title)
            }
        }
    )

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
                modifier = Modifier.clickable {
                    showDialog = true
                }
            ) {
                Text(
                    text = "${selectedDate.month}, ${selectedDate.year}",
                    color = Theme.color.body,
                    style = Theme.textStyle.label.medium
                )
                Icon(
                    painter = painterResource(R.drawable.icon_left_arrow),
                    contentDescription = "back",
                    tint = Theme.color.body,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(-90f)
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
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            items(daysInMonth) { date ->
                DayItem(
                    dayDate = date,
                    isSelected = date == selectedDate,
                    onClick = { onDaySelected(date) }
                )
            }
        }

        if (showDialog) {
            CustomDatePickerDialog(
                onDateSelected = { selectedDateMillis: Long? ->
                    selectedDateMillis?.let {
                        val date = DateFormater.formatLongToDate(selectedDateMillis)
                        onDateSelected(date)
                        daysInMonth = (DateFormater.getLocalDatesInMonth(date.year, date.monthNumber))
                    }
                },
                onDismiss = { showDialog = false },
                initialSelectedDate = selectedDate
            )
        }

        var selectedTab by remember { mutableIntStateOf(0) }

        TudeeTabs(
            tabs,
            selectedTabIndex = selectedTab,
            onTabSelected = { selectedTab = it },
            modifier = Modifier.fillMaxSize())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
private fun TasksScreenPreview() {

    var selectedDate by remember {
        mutableStateOf(
            Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        )
    }

    TasksScreen(
        selectedDate,
        onDaySelected = { date -> selectedDate = date },
        onDateSelected = { date -> selectedDate = date }
    )
}