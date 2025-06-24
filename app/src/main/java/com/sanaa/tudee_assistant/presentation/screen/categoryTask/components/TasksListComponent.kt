package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryTaskCard
import com.sanaa.tudee_assistant.presentation.designSystem.component.DateChip
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.state.TaskUiState

@Composable
fun TasksListComponent(
    tasks: List<TaskUiState>,
    category: CategoryUiState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.surface),
        contentPadding = PaddingValues(
            vertical = Theme.dimension.regular, horizontal = Theme.dimension.medium
        ),
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
    ) {
        itemsIndexed(tasks, key = { _, item -> item.hashCode() }) { _, task ->
            CategoryTaskCard(
                task = task, categoryImagePath = category.imagePath,
                onClick = {},
                taskDateAndPriority = {
                    Row(
                        modifier = Modifier,
                        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DateChip(
                            date = task.dueDate,
                        )
                        PriorityTag(
                            priority = task.priority,
                            enabled = false
                        )
                    }
                }
            )
        }
    }
}
