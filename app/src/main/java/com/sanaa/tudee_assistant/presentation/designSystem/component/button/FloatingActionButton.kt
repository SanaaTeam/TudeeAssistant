package com.sanaa.tudee_assistant.presentation.designSystem.component.button

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils.SpinnerIcon
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme


@Composable
fun FloatingActionButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    @DrawableRes iconRes: Int = R.drawable.ic_loading,
    onClick: () -> Unit = {},
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (enabled) Color.Transparent else Theme.color.disable,
        label = "fabBackgroundColor"
    )

    val iconColor by animateColorAsState(
        targetValue = if (enabled) Theme.color.onPrimary else Theme.color.stroke,
        label = "fabIconColor"
    )
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(
                brush = if (enabled) Theme.color.primaryGradient  else SolidColor(backgroundColor)
            )
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "fabContentAnimation"
        ) { loading ->
            if (loading) {
                SpinnerIcon(tint = iconColor)
            } else {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}


@PreviewLightDark
@Composable
private fun FloatingActionButtonDarkPreview(modifier: Modifier = Modifier) {
    TudeeTheme(isSystemInDarkTheme()) {
        Column {
            FloatingActionButton(
                enabled = true,
                isLoading = false,
                onClick = {},
                iconRes = R.drawable.ic_loading
            )

            FloatingActionButton(
                enabled = true,
                isLoading = true,
                onClick = {},
                iconRes = R.drawable.ic_loading
            )

            FloatingActionButton(
                enabled = false,
                isLoading = true,
                onClick = {},
                iconRes = R.drawable.ic_loading
            )

            FloatingActionButton(
                enabled = false,
                isLoading = false,
                onClick = {},
                iconRes = R.drawable.ic_loading
            )
        }
    }
}