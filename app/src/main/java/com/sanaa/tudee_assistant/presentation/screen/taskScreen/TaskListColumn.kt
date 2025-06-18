package com.sanaa.tudee_assistant.presentation.screen.taskScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus

@Composable
fun TaskListColumn(
    taskList: List<TaskUiModel>,
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
                        onTaskSwipe(task.id!!)
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
                            .background(
                                Theme.color.errorVariant,
                                RoundedCornerShape(Theme.dimension.medium)
                            )
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
        TaskUiModel(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS
        ),
        TaskUiModel(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = null,
            categoryImagePath = "file:///android_asset/categories/agriculture.png",
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS
        ),
    )
    var itemsState by remember { mutableStateOf(items) }

    TaskListColumn(itemsState, onTaskSwipe = { id ->
        itemsState = itemsState.filterNot { item -> item.id == id }
        false
    }
    )
}