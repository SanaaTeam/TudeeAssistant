package com.sanaa.tudee_assistant.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.design_system.component.TabItem
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeScrollableTabs
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.CategoryTaskState
import com.sanaa.tudee_assistant.presentation.model.TaskPriority

@Composable
fun CategoryTaskScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    categoryName: String = "Coding"
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val inProgressTasks = listOf(
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = null,
            date = "12-03-2025",
            priority = TaskPriority.LOW
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.HIGH
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.HIGH
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = "12-03-2025",
            priority = TaskPriority.MEDIUM
        ),
    )

    val todoTasks = listOf(
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Plan Weekend Activities",
            description = "Organize fun activities for the weekend",
            date = "15-03-2025",
            priority = TaskPriority.LOW
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Grocery Shopping",
            description = null,
            date = "14-03-2025",
            priority = TaskPriority.MEDIUM
        )
    )
    val doneTasks = listOf(
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Complete Assignment",
            description = "Finished the math homework",
            date = "10-03-2025",
            priority = TaskPriority.HIGH
        )
    )
    val tabs = listOf(
        TabItem(
            label = stringResource(R.string.in_progress_task_status),
            count = inProgressTasks.size
        ) {
            TaskList(tasks = inProgressTasks)
        },
        TabItem(
            label = stringResource(R.string.todo_task_status),
            count = todoTasks.size
        ) {
            TaskList(tasks = todoTasks)
        },
        TabItem(
            label = stringResource(R.string.done_task_status),
            count = doneTasks.size
        ) {
            TaskList(tasks = doneTasks)
        }
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface)
            .windowInsetsPadding(WindowInsets.statusBars) 
    ) {
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(Theme.color.surfaceHigh)
                .padding(horizontal = Theme.dimension.medium),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(color = Theme.color.stroke, width = 1.dp, shape = CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    
                    Icon(
                        painter = painterResource(R.drawable.arrow_left),
                        contentDescription = "Back",
                        tint = Theme.color.body,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = categoryName,
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title
                )
            }
        }

        
        TudeeScrollableTabs(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { selectedTabIndex = it },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun TaskList(
    tasks: List<CategoryTaskState>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        contentPadding = PaddingValues(Theme.dimension.medium),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
    ) {
        items(tasks) { task ->
            CategoryTaskCard(categoryTask = task)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryTaskScreenPreview() {
    TudeeTheme(isDarkTheme = false) {
        CategoryTaskScreen(
            onBackClick = {},
            categoryName = "Coding"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CategoryTaskScreenDarkPreview() {
    TudeeTheme(isDarkTheme = true) {
        CategoryTaskScreen(
            onBackClick = {},
            categoryName = "Coding"
        )
    }
}