package com.sanaa.tudee_assistant.presentation.screen.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.TasksScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.util.navigateWithRestoreState

@Composable
fun Title(
    text: String,
    tasksCount: Int,
    taskUiStatus: TaskUiStatus,
    onNavigateToTaskScreen: (TaskUiStatus) -> Unit,
) {
    val navController = AppNavigation.mainScreen

    Row(
        modifier = Modifier
            .background(Theme.color.surface)
            .padding(horizontal = Theme.dimension.medium, vertical = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            color = Theme.color.title,
            style = Theme.textStyle.title.large
        )

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .clickable {
                    navController.navigateWithRestoreState(TasksScreenRoute(taskUiStatus))
                    onNavigateToTaskScreen(taskUiStatus)
                }
                .background(Theme.color.surfaceHigh)
                .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 2.dp),
                text = tasksCount.toString(),
                color = Theme.color.body,
                style = Theme.textStyle.label.small
            )

            Icon(
                painter = painterResource(R.drawable.nex_arrow),
                tint = Theme.color.body,
                contentDescription = null
            )
        }
    }
}