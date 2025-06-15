package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composables.HorizontalSpace
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Priority
import com.sanaa.tudee_assistant.presentation.model.Priority.High
import com.sanaa.tudee_assistant.presentation.model.Priority.Low
import com.sanaa.tudee_assistant.presentation.model.Priority.Medium

@Composable
fun PriorityTag(
    priority: Priority,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                when (priority) {
                    High -> Theme.color.pinkAccent
                    Medium -> Theme.color.yellowAccent
                    Low -> Theme.color.greenAccent
                }
            )
            .padding(vertical = 6.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(12.dp),
            painter = painterResource(
                id = when (priority) {
                    High -> R.drawable.flag
                    Medium -> R.drawable.alert_01
                    Low -> R.drawable.trade_down
                }
            ),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Theme.color.onPrimary)
        )

        HorizontalSpace(2.dp)

        Text(
            modifier = Modifier,
            text = when (priority) {
                High -> "High"
                Medium -> "Medium"
                Low -> "Low"
            },
            color = Theme.color.onPrimary,
            style = Theme.textStyle.label.small
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme(isDarkTheme = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaceHigh)
                .padding(16.dp)
        ) {
            PriorityTag(modifier = Modifier.padding(10.dp), priority = High)
            PriorityTag(modifier = Modifier.padding(10.dp), priority = Medium)
            PriorityTag(modifier = Modifier.padding(10.dp), priority = Low)
        }
    }
}