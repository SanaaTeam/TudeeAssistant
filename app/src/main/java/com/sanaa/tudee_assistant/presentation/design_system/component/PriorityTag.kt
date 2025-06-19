package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
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
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .clickable(enabled = enabled) {
                onClick.invoke()
            }
            .background(
                if (isSelected) {
                    when (priority) {
                        HIGH -> Theme.color.pinkAccent
                        MEDIUM -> Theme.color.yellowAccent
                        LOW -> Theme.color.greenAccent
                    }
                } else {
                    Theme.color.surfaceLow
                }
            )
            .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            modifier = Modifier.size(Theme.dimension.regular),
            painter = painterResource(
                id = when (priority) {
                    HIGH -> R.drawable.flag
                    MEDIUM -> R.drawable.alert_01
                    LOW -> R.drawable.trade_down
                }
            ),
            contentDescription = null,
            tint = if (isSelected) Theme.color.onPrimary else Theme.color.hint
        )

        Text(
            text = when (priority) {
                HIGH -> stringResource(R.string.high)
                MEDIUM -> stringResource(R.string.medium)
                LOW -> stringResource(R.string.low)
            },
            color = if (isSelected) Theme.color.onPrimary else Theme.color.hint,
            style = Theme.textStyle.label.small
        )
    }
}

@Preview
@Composable
private fun PreviewPriorityTag() {
    TudeeTheme(false) {
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