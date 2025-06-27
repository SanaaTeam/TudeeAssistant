package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun TudeeBottomNavBar(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .shadow(Theme.dimension.regular)
            .background(Theme.color.surfaceHigh)
            .fillMaxWidth()
            .height(74.dp)
            .padding(horizontal = Theme.dimension.extraLarge),
        Arrangement.SpaceBetween,
        Alignment.CenterVertically
    ) { content() }
}

@Composable
fun TudeeBottomNavBarItem(
    selected: Boolean,
    @DrawableRes iconRes: Int,
    @DrawableRes selectedIconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .background(if (selected) Theme.color.primaryVariant else Color.Transparent)
            .size(42.dp)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(Theme.dimension.large),
            painter = painterResource(id = if (selected) selectedIconRes else iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(
                if (selected) Theme.color.primary else Theme.color.hint
            )
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewTudeeNavBar() {
    TudeeTheme(isDark = isSystemInDarkTheme()) {
        var selectedScreen by remember { mutableIntStateOf(0) }
        Box(modifier = Modifier.fillMaxSize()) {
            TudeeBottomNavBar(modifier = Modifier.align(Alignment.BottomCenter)) {
                TudeeBottomNavBarItem(
                    selected = selectedScreen == 0,
                    iconRes = R.drawable.home,
                    selectedIconRes = R.drawable.home_fill
                ) {
                    selectedScreen = 0
                }

                TudeeBottomNavBarItem(
                    selected = selectedScreen == 1,
                    iconRes = R.drawable.profile,
                    selectedIconRes = R.drawable.profile_fill
                ) {
                    selectedScreen = 1
                }

                TudeeBottomNavBarItem(
                    selected = selectedScreen == 2,
                    iconRes = R.drawable.menu,
                    selectedIconRes = R.drawable.menu_fill
                ) {
                    selectedScreen = 2
                }
            }
        }
    }
}
