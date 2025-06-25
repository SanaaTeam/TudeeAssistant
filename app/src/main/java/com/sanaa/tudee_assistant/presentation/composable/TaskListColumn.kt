package com.sanaa.tudee_assistant.presentation.composable

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
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskItemCard
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider

@Composable
fun TaskListColumn(
    taskList: List<TaskUiState>,
    categories: List<CategoryUiState>,
    onTaskSwipe: (TaskUiState) -> Boolean = { true },
    onTaskClick: (TaskUiState) -> Unit,
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
        itemsIndexed(taskList, key = { _, item -> item.hashCode() }) { _, task ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { onTaskSwipe(task) },
                positionalThreshold = { it * 20f }
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
                    TaskItemCard(
                        task = task,
                        categoryImagePath = categories
                            .firstOrNull { it.id == task.categoryId }
                            ?.imagePath ?: "",
                        onClick = { onTaskClick(task) },
                        taskDateAndPriority = {
                            PriorityTag(
                                modifier = Modifier.padding(start = Theme.dimension.extraSmall),
                                priority = task.priority,
                                enabled = false
                            )
                        }
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(Theme.dimension.medium))
                    .animateItem(fadeInSpec = null, fadeOutSpec = null)
            )
        }
    }
}

@Preview
@Composable
private fun TaskListColumnPreview() {
    var itemsState by remember { mutableStateOf(DataProvider.getTasksSample()) }

    TudeeTheme(isDark = true) {
        TaskListColumn(
            taskList = itemsState,
            categories = emptyList(),
            onTaskSwipe = { task ->
                itemsState = itemsState.filterNot { item -> item == task }
                false
            },
            onTaskClick = {}
        )
    }
}