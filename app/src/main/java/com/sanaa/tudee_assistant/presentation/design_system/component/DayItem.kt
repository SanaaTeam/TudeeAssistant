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
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.getShortDayName
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DayItem(
    dayDate: LocalDate,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val backgroundBrush = Brush.verticalGradient(
        listOf(
            Theme.color.primary,
            Theme.color.primaryGradientStart,
            Theme.color.primaryGradientEnd,
        )
    )

    Box(modifier = modifier.clip(RoundedCornerShape(Theme.dimension.medium)).clickable { onClick() }) {
        Column(
            modifier = Modifier
                .width(56.dp)
                .then(
                    if (isSelected) Modifier.background(backgroundBrush)
                    else Modifier.background(Theme.color.surface)
                )
                .padding(vertical = Theme.dimension.regular),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = dayDate.dayOfMonth.toString(),
                color = if (isSelected) Theme.color.onPrimary else Theme.color.body,
                style = Theme.textStyle.title.medium
            )

            Text(
                text = dayDate.getShortDayName(),
                color = if (isSelected) Theme.color.onPrimaryCaption else Theme.color.hint,
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
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            DayItem(
                dayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                isSelected = true
            ) {}
            DayItem(
                dayDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
                isSelected = false
            ) {}
        }
    }
}