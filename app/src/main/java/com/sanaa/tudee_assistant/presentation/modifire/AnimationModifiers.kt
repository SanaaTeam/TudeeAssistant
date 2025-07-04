package com.sanaa.tudee_assistant.presentation.modifire

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class SlideDirection {
    Up, Down, End, Start, Left, Right
}

fun Modifier.applyTaskCardAnimation(
    delayMillis: Int,
    durationMillis: Int = 600,
    distance: Dp = 10.dp
): Modifier = this
    .fadeAnimation(durationMillis = 400)
    .slide(
        direction = SlideDirection.Up,
        delayMillis = delayMillis,
        durationMillis = durationMillis,
        distance = distance
    )


fun Modifier.slide(
    direction: SlideDirection,
    distance: Dp = 10.dp,
    durationMillis: Int = 550,
    delayMillis: Int = 0,
    animationSpec: AnimationSpec<Float> = tween(durationMillis = durationMillis)
): Modifier = composed {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    var isAnimated by remember { mutableStateOf(false) }
    val offsetX by animateFloatAsState(
        targetValue = if (isAnimated) {
            0f
        } else {
            when (direction) {
                SlideDirection.Start -> if (isRtl) distance.value else -distance.value
                SlideDirection.End -> if (isRtl) -distance.value else distance.value
                SlideDirection.Left -> distance.value
                SlideDirection.Right -> -distance.value
                else -> 0f
            }
        },
        animationSpec = animationSpec,
        label = "swipeAnimationX"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (isAnimated) {
            0f
        } else {
            when (direction) {
                SlideDirection.Up -> distance.value
                SlideDirection.Down -> -distance.value
                else -> 0f
            }
        },
        animationSpec = animationSpec,
        label = "swipeAnimationY"
    )

    LaunchedEffect(key1 = Unit) {
        delay(delayMillis.toLong())
        isAnimated = true
    }

    this.offset(x = offsetX.dp, y = offsetY.dp)
}

fun Modifier.fadeAnimation(
    from: Float = 0f,
    to: Float = 1f,
    durationMillis: Int = 400
): Modifier = composed {
    var isAnimated by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if (isAnimated) to else from,
        animationSpec = tween(durationMillis = durationMillis),
        label = "fadeAnimation"
    )

    LaunchedEffect(Unit) {
        isAnimated = true
    }

    graphicsLayer {
        this.alpha = alpha
    }
}