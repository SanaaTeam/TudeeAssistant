package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.CategoryTaskState
import com.sanaa.tudee_assistant.presentation.model.TaskPriority

@Composable
fun TaskListColumn(
    taskList: List<CategoryTaskState>,
    onTaskSwipe: (Int) -> Boolean = { true }
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        contentPadding = PaddingValues(
            vertical = Theme.dimension.regular,
            horizontal = Theme.dimension.medium
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
    ) {
        itemsIndexed(taskList) { index, task ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { dismissValue ->
                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                        onTaskSwipe(index)
                    } else false
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                enableDismissFromEndToStart = true,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Theme.color.errorVariant)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_delete),
                            contentDescription = null,
                            tint = Theme.color.error
                        )
                    }
                },
                content = {
                    CategoryTaskCard(task)
                }
            )
        }
    }
}

@Preview
@Composable
private fun TaskListColumnPreview() {
    val items = listOf(
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            date = null,
            priority = TaskPriority.MEDIUM
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            date = "12-03-2025",
            priority = TaskPriority.LOW
        ),
        CategoryTaskState(
            icon = painterResource(R.drawable.birthday_cake),
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow morning...",
            date = "12-03-2025",
            priority = TaskPriority.HIGH
        ),
    )
    var itemsState by remember { mutableStateOf(items) }

    TaskListColumn(itemsState, onTaskSwipe = { index ->
        itemsState = itemsState.filterNot { item -> item == itemsState[index] }
         false
    }
    )
}