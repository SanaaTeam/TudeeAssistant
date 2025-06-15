package com.sanaa.tudee_assistant.presentation.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.component.button.utils.SpinnerIcon
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme


@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row (
        modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = label,
            style = Theme.textStyle.label.large
                .copy(
                    color = when(enabled){
                        true -> Theme.color.primary
                        false -> Theme.color.disable
                    }
                )
        )
        if (isLoading){
            SpinnerIcon(tint = when(enabled){
                true -> Theme.color.primary
                false -> Theme.color.disable
            })
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TextButtonP(modifier: Modifier = Modifier) {
    TextButton(
        label = "Cancel",
        enabled = false,
        isLoading = true,
        onClick = {}
    )
}