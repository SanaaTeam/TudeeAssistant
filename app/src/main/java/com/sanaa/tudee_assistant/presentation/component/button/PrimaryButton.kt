package com.sanaa.tudee_assistant.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.component.button.utils.SpinnerIcon
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme


@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {


    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .then(
                when (enabled) {
                    true -> Modifier.background(
                        brush = Brush.linearGradient(
                            listOf(
                                Theme.color.primaryGradientStart,
                                Theme.color.primaryGradientEnd
                            )
                        )
                    )
                    false -> Modifier.background(color = Theme.color.disable)
                }
            )

            .padding(horizontal = 24.dp, vertical = 18.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Text(
            text = label,
            style = Theme.textStyle.label.large
                .copy(
                    color = when (enabled) {
                        true -> Theme.color.onPrimary
                        false -> Theme.color.stroke
                    }
                )
        )


        if (isLoading) {
            SpinnerIcon(
                tint = when (enabled) {
                    true -> Theme.color.onPrimary
                    false -> Theme.color.stroke
                }
            )
        }


    }
}


@Preview()
@Composable
fun PrimaryButtonP(modifier: Modifier = Modifier) {
    Box(modifier.padding(top = 8.dp, start = 8.dp)) {
        PrimaryButton(
            label = "Submit",
            isLoading = true,
            enabled = true,
            onClick = {},
        )
    }

}

