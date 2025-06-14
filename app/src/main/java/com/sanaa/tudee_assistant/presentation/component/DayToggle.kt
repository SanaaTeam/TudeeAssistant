package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.composables.VerticalSpace
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun DayToggle(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .width(56.dp)
                .then(
                    if (selected) {
                        Modifier.background(
                            Brush.verticalGradient(
                                listOf(
                                    Theme.color.primary,
                                    Theme.color.primaryGradientStart,
                                    Theme.color.primaryGradientEnd,
                                )
                            )
                        )
                    } else {
                        Modifier.background(Theme.color.surface)
                    }
                )
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = "15",
                color = if (selected) Theme.color.onPrimary else Theme.color.body,
                style = Theme.textStyle.title.medium
            )

            VerticalSpace(2.dp)

            Text(
                modifier = Modifier,
                text = "Mon",
                color = if (selected) Theme.color.onPrimaryCaption else Theme.color.hint,
                style = Theme.textStyle.body.small
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    var selectState by remember { mutableStateOf(false) }
    var selectState2 by remember { mutableStateOf(false) }

    TudeeTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DayToggle(selectState) {
                selectState = !selectState
            }
            DayToggle(selectState2) {
                selectState2 = !selectState2
            }
        }
    }
}