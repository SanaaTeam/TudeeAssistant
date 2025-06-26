package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.CherryBomb
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    tailComponent: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.primary)
            .padding(vertical = Theme.dimension.regular, horizontal = Theme.dimension.medium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
        ) {

            Image(
                painter = painterResource(R.drawable.icon_app_main_icon),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(48.dp)
            )

            Column {
                Text(
                    text = stringResource(R.string.logoName),
                    fontFamily = CherryBomb,
                    fontWeight = FontWeight(400),
                    fontSize = 18.sp,
                    color = Theme.color.onPrimary,
                )
                Text(
                    text = stringResource(R.string.appBarCaption),
                    style = Theme.textStyle.label.small,
                    color = Theme.color.onPrimaryCaption
                )
            }
        }

        tailComponent()
    }
}

@Preview(widthDp = 360)
@Composable
private fun AppBarLightPreview() {

    var isDarkMode by remember { mutableStateOf(false) }

    TudeeTheme(isDarkMode) {

        AppBar(
            tailComponent = {
                DarkModeThemeSwitchButton(
                    isDarkMode,
                    800,
                    onCheckedChange = { isDarkMode = !isDarkMode })
            }
        )
    }
}

@Preview(widthDp = 360)
@Composable
private fun AppBarDarkPreview() {

    var isDarkMode by remember { mutableStateOf(true) }

    TudeeTheme(isDarkMode) {

        AppBar(
            tailComponent = {
                DarkModeThemeSwitchButton(
                    isDarkMode,
                    800,
                    onCheckedChange = { isDarkMode = !isDarkMode })
            }
        )
    }
}