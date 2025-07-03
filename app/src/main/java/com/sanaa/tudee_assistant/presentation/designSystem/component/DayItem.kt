package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.utils.DateFormater.getShortDayName
import com.sanaa.tudee_assistant.presentation.utils.DateUtil
import kotlinx.datetime.LocalDate

@Composable
fun DayItem(
    dayDate: LocalDate,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit = {},
) {
    val backgroundBrush = Theme.color.primaryGradient

    Column(
        modifier
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .clickable { onClick() }
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

@PreviewLightDark
@Composable
private fun Preview() {
    TudeeTheme(isSystemInDarkTheme()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
                .padding(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            DayItem(
                dayDate = DateUtil.today.date,
                isSelected = true
            ) {}
            DayItem(
                dayDate = DateUtil.today.date,
                isSelected = false
            ) {}
        }
    }
}