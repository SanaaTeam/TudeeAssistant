package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.designSystem.component.TaskItemCard
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.CategoryUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiState

@Composable
fun TasksListComponent(
    tasks: List<TaskUiState>,
    category: CategoryUiState,
    onTaskClicked: (TaskUiState) -> Unit,
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
            TaskItemCard(
                task = task, categoryImagePath = category.imagePath,
                onClick = onTaskClicked,
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

@Composable
 fun DateChip(date: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Theme.color.surface)
            .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            modifier = Modifier.size(Theme.dimension.regular),
            painter = painterResource(id = R.drawable.calendar_favorite_01),
            contentDescription = null,
            tint = Theme.color.body
        )

        Text(
            text = date,
            color = Theme.color.body,
            style = Theme.textStyle.label.small
        )
    }
}