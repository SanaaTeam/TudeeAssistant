package com.sanaa.tudee_assistant.presentation.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
fun SecondaryButton(
    modifier : Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
    ) {
        val interactionSource = remember { MutableInteractionSource() }


        Row(
            modifier = modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick
                )
                .clip(RoundedCornerShape(100.dp))
                .border(
                    width = 1.dp,
                    color = Theme.color.stroke,
                    shape = RoundedCornerShape(100.dp)
                ).padding(horizontal = 24.dp, vertical = 18.5.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Text(
                text = text,
                style = Theme.textStyle.label.large
                    .copy(
                        color = when (enabled) {
                            true -> Theme.color.primary
                            false -> Theme.color.stroke
                        }
                    )
            )


            if (isLoading) {
                SpinnerIcon(
                    tint = when (enabled) {
                        true -> Theme.color.primary
                        false -> Theme.color.stroke
                    }
                )
            }


        }
    }


@Preview(showBackground = true)
@Composable
fun SecondaryButtonP(modifier: Modifier = Modifier) {
    SecondaryButton(
        text = "Submit",
        enabled = true,
        isLoading = true,
        onClick = {  }
    )
}