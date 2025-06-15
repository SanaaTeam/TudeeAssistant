package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.composables.VerticalSpace
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Day
import com.sanaa.tudee_assistant.presentation.util.DateFormater.getDayName
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DayItem(
    day: Day,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val backgroundBrush = Brush.verticalGradient(
        listOf(
            Theme.color.primary,
            Theme.color.primaryGradientStart,
            Theme.color.primaryGradientEnd,
        )
    )

    Box(
        modifier = modifier
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(56.dp)
                .then(
                    if (day.isSelected) Modifier.background(backgroundBrush)
                    else Modifier.background(Theme.color.surface)
                )
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = day.dayDate.dayOfMonth.toString(),
                color = if (day.isSelected) Theme.color.onPrimary else Theme.color.body,
                style = Theme.textStyle.title.medium
            )

            VerticalSpace(2.dp)

            Text(
                modifier = Modifier,
                text = day.dayDate.getDayName(),
                color = if (day.isSelected) Theme.color.onPrimaryCaption else Theme.color.hint,
                style = Theme.textStyle.body.small
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    TudeeTheme(isDarkTheme = true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DayItem(
                Day(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    isSelected = true
                )
            ) {}
            DayItem(
                Day(
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    isSelected = false
                )
            ) {}
        }
    }
}