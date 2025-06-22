package com.sanaa.tudee_assistant.presentation.designSystem.component.button.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.sanaa.tudee_assistant.R


@Composable
fun SpinnerIcon(modifier: Modifier = Modifier, tint: Color) {
    val infiniteTransition = rememberInfiniteTransition()

    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )

    Icon(
        modifier = modifier.rotate(rotation.value),
        painter = painterResource(R.drawable.loading_spiner),
        contentDescription = stringResource(R.string.loading_spinner),
        tint = tint
    )
}