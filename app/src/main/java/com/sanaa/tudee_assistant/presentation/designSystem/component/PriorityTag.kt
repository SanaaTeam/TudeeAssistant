package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority.HIGH
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority.LOW
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority.MEDIUM

@Composable
fun PriorityTag(
    priority: TaskUiPriority,
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    val backgroundColor by animateColorAsState(
        targetValue = getPriorityColor(priority, isSelected),
        label = "priorityTagBackground"
    )
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .clickable(enabled = enabled) {
                onClick.invoke()
            }
            .background(backgroundColor)
            .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            modifier = Modifier.size(Theme.dimension.regular),
            painter = painterResource(
                id = getPriorityIcon(priority)
            ),
            contentDescription = null,
            tint = if (isSelected) Theme.color.onPrimary else Theme.color.hint
        )

        Text(
            text = getPriorityLabel(priority),
            color = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
            style = Theme.textStyle.label.small
        )
    }
}

@Composable
fun getPriorityColor(priority: TaskUiPriority, isSelected: Boolean): Color =
    if (!isSelected) Theme.color.surfaceLow else when (priority) {
        HIGH -> Theme.color.pinkAccent
        MEDIUM -> Theme.color.yellowAccent
        LOW -> Theme.color.greenAccent
    }

fun getPriorityIcon(priority: TaskUiPriority): Int = when (priority) {
    HIGH -> R.drawable.flag
    MEDIUM -> R.drawable.alert
    LOW -> R.drawable.trade_down
}

@Composable
fun getPriorityLabel(priority: TaskUiPriority): String = when (priority) {
    HIGH -> stringResource(R.string.high)
    MEDIUM -> stringResource(R.string.medium)
    LOW -> stringResource(R.string.low)
}


@PreviewLightDark
@Composable
private fun PreviewPriorityTag() {
    TudeeTheme(isSystemInDarkTheme()) {
        var isSelected by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaceHigh)
                .padding(Theme.dimension.medium)
        ) {
            PriorityTag(modifier = Modifier.padding(10.dp), priority = HIGH)
            PriorityTag(modifier = Modifier.padding(10.dp), priority = MEDIUM, enabled = false)
            PriorityTag(modifier = Modifier.padding(10.dp), priority = LOW, enabled = true) {}
            PriorityTag(
                modifier = Modifier.padding(10.dp),
                priority = LOW,
                isSelected = isSelected
            ) {
                isSelected = !isSelected
            }
            PriorityTag(
                modifier = Modifier.padding(10.dp),
                priority = LOW,
                isSelected = false,
            )
        }
    }
}