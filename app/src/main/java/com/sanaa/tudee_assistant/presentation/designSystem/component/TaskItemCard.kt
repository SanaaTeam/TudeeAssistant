package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.composable.CategoryThumbnail
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.components.DateChip
import com.sanaa.tudee_assistant.presentation.utils.DataProvider

@Composable
fun TaskItemCard(
    task: TaskUiState,
    categoryImagePath: String,
    onClick: (TaskUiState) -> Unit,
    modifier: Modifier = Modifier,
    taskDateAndPriority: @Composable RowScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .clickable { onClick(task) }
            .background(Theme.color.surfaceHigh)
            .padding(
                start = Theme.dimension.extraSmall,
                top = Theme.dimension.extraSmall,
                end = Theme.dimension.regular
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                CategoryThumbnail(
                    imagePath = categoryImagePath,
                    modifier = Modifier.size(Theme.dimension.extraLarge)
                )
            }

            Row {
                taskDateAndPriority()
            }

        }

        Column(
            modifier = Modifier.padding(start = Theme.dimension.small),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = task.title,
                color = Theme.color.body,
                style = Theme.textStyle.label.large,
                maxLines = 1,
            )

            task.description?.let {
                Text(
                    modifier = Modifier.padding(bottom = Theme.dimension.regular),
                    text = task.description,
                    color = Theme.color.hint,
                    style = Theme.textStyle.label.small,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun Preview() {
    TudeeTheme(isSystemInDarkTheme()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentPadding = PaddingValues(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            items(DataProvider.getTasksSample()) {
                TaskItemCard(
                    task = it,
                    categoryImagePath = "file:///android_asset/categories/agriculture.png",
                    onClick = {},
                    taskDateAndPriority = {
                        DateChip(it.dueDate)
                        PriorityTag(
                            modifier = Modifier.padding(start = Theme.dimension.extraSmall),
                            priority = it.priority,
                            enabled = false
                        )
                    }
                )
            }
        }
    }
}