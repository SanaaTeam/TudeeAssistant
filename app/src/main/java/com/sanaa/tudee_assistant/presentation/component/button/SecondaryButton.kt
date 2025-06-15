package com.sanaa.tudee_assistant.presentation.component.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.component.button.utils.ButtonContent
import com.sanaa.tudee_assistant.presentation.component.button.utils.SpinnerIcon
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun SecondaryButton(
    modifier : Modifier = Modifier,
    lable: String,
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
                .border(
                    width = 1.dp,
                    color = when(enabled){
                        true -> Theme.color.stroke
                        false -> Theme.color.disable
                    },
                    shape = RoundedCornerShape(100.dp)
                ).padding(horizontal = 24.dp, vertical = 18.5.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {

            ButtonContent(
                label = lable,
                isLoading = isLoading,
                style = Theme.textStyle.label.large
                    .copy(
                        color = when (enabled) {
                            true -> Theme.color.primary
                            false -> Theme.color.stroke
                        }
                    ),
                spinnerTint = when (enabled) {
                        true -> Theme.color.primary
                        false -> Theme.color.stroke
                    }
            )



        }
    }


@Preview(showBackground = true)
@Composable
fun SecondaryButtonLight(modifier: Modifier = Modifier) {
    TudeeTheme (isDarkTheme = false){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(
                lable = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {  }
            )
            SecondaryButton(
                lable = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {  }
            )

            SecondaryButton(
                lable = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {  }
            )

            SecondaryButton(
                lable = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {  }
            )



        }
    }

}


@Preview(showBackground = true)
@Composable
fun SecondaryButtonDark(modifier: Modifier = Modifier) {
    TudeeTheme (isDarkTheme = true){
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SecondaryButton(
                lable = "Submit",
                enabled = true,
                isLoading = true,
                onClick = {  }
            )
            SecondaryButton(
                lable = "Submit",
                enabled = true,
                isLoading = false,
                onClick = {  }
            )

            SecondaryButton(
                lable = "Submit",
                enabled = false,
                isLoading = false,
                onClick = {  }
            )

            SecondaryButton(
                lable = "Submit",
                enabled = false,
                isLoading = true,
                onClick = {  }
            )



        }
    }

}
