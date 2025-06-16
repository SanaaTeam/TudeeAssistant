package com.sanaa.tudee_assistant.presentation.design_system.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sanaa.tudee_assistant.presentation.design_system.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun NegativeTextButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick:()-> Unit
) {

    val contentColor = when(enabled){
        true -> Theme.color.error
        false -> Theme.color.stroke
    }

    Row (
        modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically
    ){
        ButtonContent(
            label = label,
            enabled = enabled,
            isLoading = isLoading,
            contentColor = contentColor
        )
    }


}

@Preview
@Composable
private fun NegativeTextButtonLightPreview(modifier: Modifier = Modifier) {
    TudeeTheme (isDarkTheme = false){
        Column (
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
        ){
            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {}
            )



            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {}
            )
            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {}
            )


        }
    }
}




@Preview
@Composable
private fun NegativeTextButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme (isDarkTheme = true){
        Column (
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.small)
        ){
            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {}
            )

            NegativeTextButton(
                label = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {}
            )



            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {}
            )
            NegativeTextButton(
                label = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {}
            )


        }
    }
}