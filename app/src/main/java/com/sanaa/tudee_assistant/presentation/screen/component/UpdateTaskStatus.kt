package com.sanaa.tudee_assistant.presentation.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryImageContainer
import com.sanaa.tudee_assistant.presentation.design_system.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.design_system.component.TaskStatusCard
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskPriority
import com.sanaa.tudee_assistant.presentation.model.TaskStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTaskStatus(modifier: Modifier = Modifier) {
    BaseBottomSheet(
        onDismiss = {},
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        content = {
            Column(
                modifier = modifier.padding(Theme.dimension.medium)
            ) {
                Text(
                    text = "Task Details",
                    modifier = Modifier.padding(bottom = 6.dp),
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title,
                )
                CategoryImageContainer(
                    painter = painterResource(R.drawable.education_cat)
                )
                Column(
                    modifier = Modifier.padding(Theme.dimension.small),
                    verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                ) {
                    Text(
                        text = "Organize Study Desk",
                        modifier = Modifier,
                        style = Theme.textStyle.title.medium,
                        color = Theme.color.title,

                        )
                    Text(
                        text = "Solve all exercises from page 45 to 50 in the textbook, Solve all" +
                                " exercises from page 45 to 50 in the textbook.",
                        modifier = Modifier,
                        style = Theme.textStyle.body.small,
                        color = Theme.color.body,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Theme.dimension.regular),
                    thickness = 1.dp,
                    color = Theme.color.stroke
                )
                Row(
                    modifier = Modifier.padding(
                        top = Theme.dimension.regular,
                        bottom = Theme.dimension.regular
                    ),
                    horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                ) {
                    TaskStatusCard(taskStatus = TaskStatus.IN_PROGRESS)
                    PriorityTag(
                        priority = TaskPriority.HIGH,
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUpdateTaskStatus() {
    TudeeTheme {
        UpdateTaskStatus()
    }
}
