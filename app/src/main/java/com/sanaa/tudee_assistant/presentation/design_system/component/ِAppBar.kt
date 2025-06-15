package com.sanaa.tudee_assistant.presentation.design_system.component

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.CherryBomb
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun AppBar(
    title: String,
    caption: String,
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    onSwitchClick: (Boolean) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.primary)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.icon_app_main_icon),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.size(48.dp)
            )

            Column {
                Text(
                    text = title,
                    fontFamily = CherryBomb,
                    fontWeight = FontWeight(400),
                    fontSize = 18.sp,
                    color = Theme.color.onPrimary,
                )
                Text(
                    text = caption,
                    style = Theme.textStyle.label.small,
                    color = Theme.color.onPrimaryCaption
                )
            }
        }

        DarkThemeSwitch(isDarkMode, onCheckedChange = onSwitchClick)
    }
}

@Preview(widthDp = 360)
@Composable
private fun AppBarPreview() {
    TudeeTheme(isDarkTheme = false) {
        AppBar(title = "Tudee", caption = "Your cute Helper for Every Task")
    }
}