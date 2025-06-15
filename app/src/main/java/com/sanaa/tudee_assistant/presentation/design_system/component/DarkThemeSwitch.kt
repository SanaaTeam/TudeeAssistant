package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.util.dropShadow
import com.sanaa.tudee_assistant.presentation.util.innerShadow

@Composable
fun DarkThemeSwitch(
    isDarkMode: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {

    val animationSpecDurationMillis = 800

    val bgColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF151535) else Theme.color.primary,
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
    )

    Box(
        modifier = modifier
            .size(64.dp, 36.dp)
            .clip(RoundedCornerShape(100.dp))
            .background(bgColor)
            .border(1.dp, Color(0x1F1F1F1F), RoundedCornerShape(100.dp))
            .padding(horizontal = 2.dp)
            .toggleable(
                value = isDarkMode,
                onValueChange = onCheckedChange,
                role = Role.Switch,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {

        AnimatedMoon(isDarkMode, animationSpecDurationMillis)

        AnimatedSun(isDarkMode, animationSpecDurationMillis)

        FirstGreyCloud(isDarkMode)

        SecondGreyCloud(isDarkMode)

        FirstWhiteCloud(isDarkMode)

        TransformingWhiteCloud(isDarkMode, animationSpecDurationMillis)

        AnimatedTransformingMoonCircle(isDarkMode, animationSpecDurationMillis)

        AnimatedVisibility(
            isDarkMode,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = animationSpecDurationMillis,
                    easing = EaseOut
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = animationSpecDurationMillis,
                    easing = EaseOut
                )
            ),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ellipse_stars),
                        contentDescription = null,
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    MoonLargeCircle()
                    MoonSmallCircle()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DarkThemeSwitchPreview() {
    Column {

        var checkedState by remember { mutableStateOf(false) }

        DarkThemeSwitch(checkedState, Modifier.padding(4.dp)) { checkedState = !checkedState }
        DarkThemeSwitch(!checkedState, Modifier.padding(4.dp)) { checkedState = !checkedState }
    }
}



@Composable
private fun BoxScope.AnimatedSun(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int
) {
    AnimatedVisibility(
        !isDarkMode,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        modifier = Modifier.align(Alignment.CenterStart)
    ) {
        val sunColor = Brush.linearGradient(listOf(Color(0xFFF2C849), Color(0xFFF49061)))

        Box(
            modifier = Modifier
                .size(32.dp)
                .dropShadow(
                    offsetX = 1.dp,
                    offsetY = (-1).dp,
                    blur = 3.dp,
                    color = Color(0xFF79A4FD),
                )
                .background(sunColor, CircleShape)
        )
    }
}

@Composable
private fun BoxScope.AnimatedMoon(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int
) {
    AnimatedVisibility(
        isDarkMode,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        modifier = Modifier.align(Alignment.CenterEnd)
    ) {

        val moonColor = Brush.linearGradient(listOf(Color(0xFFE9F0FF), Color(0xFFE0E9FE)))

        Box(
            modifier = Modifier
                .size(32.dp)
                .dropShadow(
                    offsetX = (-1).dp,
                    offsetY = 1.dp,
                    blur = 3.dp,
                    color = Color(0xFF323297),
                )
                .background(moonColor, CircleShape)
        )
    }
}

@Composable
private fun BoxScope.MoonSmallCircle() {
    Box(
        modifier = Modifier
            .size(4.dp)
            .align(Alignment.BottomEnd)
            .offset(x = (-9).dp, y = (-4).dp)
            .clip(CircleShape)
            .align(Alignment.BottomEnd)
            .background(Color(0xFFE9EFFF))
            .innerShadow(
                shape = CircleShape,
                color = Color(0xFFBFD2FF),
                blur = 4.dp,
                offsetX = 1.dp,
                offsetY = 1.dp
            )
    )
}

@Composable
private fun BoxScope.MoonLargeCircle() {
    Box(
        modifier = Modifier
            .size(14.dp)
            .align(Alignment.BottomStart)
            .offset(x = 4.dp, y = (-6).dp)
            .clip(CircleShape)
            .background(Color(0xFFE9EFFF))
            .innerShadow(
                shape = CircleShape,
                color = Color(0xFFBFD2FF),
                blur = 4.dp,
                offsetX = 1.dp,
                offsetY = 1.dp
            )
    )
}

@Composable
private fun BoxScope.TransformingWhiteCloud(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int
) {
    AnimatedVisibility(
        !isDarkMode,
        enter = slideIn(
            initialOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2).toInt()) },
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        exit = slideOut(
            targetOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2).toInt()) },
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = (-12).dp, y = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(14.dp, 16.dp)
                .background(Color.White, RoundedCornerShape(100.dp))

        )
    }
}

@Composable
private fun BoxScope.FirstWhiteCloud(isDarkMode: Boolean) {
    AnimatedCircle(
        isDarkMode,
        modifier = Modifier.align(Alignment.BottomEnd),
        size = 16.dp,
        startOffsetX = 1.dp,
        clickedOffsetX = 50.dp,
        startOffsetY = 4.dp,
        clickedOffsetY = 50.dp
    )
}

@Composable
private fun BoxScope.SecondGreyCloud(isDarkMode: Boolean) {
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.BottomEnd),
        size = 24.dp,
        startOffsetX = (-6).dp,
        clickedOffsetX = 50.dp,
        startOffsetY = 8.dp,
        clickedOffsetY = 50.dp,
        color = Color(0xFFF0F0F0),
    )
}

@Composable
private fun BoxScope.FirstGreyCloud(isDarkMode: Boolean) {
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.TopEnd),
        size = 32.dp,
        startOffsetX = 14.dp,
        clickedOffsetX = 50.dp,
        startOffsetY = (-4).dp,
        clickedOffsetY = 50.dp,
        color = Color(0xFFF0F0F0),
    )
}

@Composable
private fun BoxScope.AnimatedTransformingMoonCircle(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {

    val moonCircleSize by animateDpAsState(
        targetValue = if (isDarkMode) 8.dp else 29.dp,
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
    )

    val circleColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFFE9EFFF) else Color(0xFFFFFFFF),
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
    )
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.TopEnd),
        size = moonCircleSize,
        startOffsetX = (15).dp,
        clickedOffsetX = (-14).dp,
        startOffsetY = (-2).dp,
        clickedOffsetY = 4.dp,
        color = circleColor,
        hasInnerShadow = true,
    )
}

@Composable
private fun AnimatedCircle(
    isClicked: Boolean = false,
    size: Dp,
    color: Color = Color.White,
    startOffsetX: Dp,
    clickedOffsetX: Dp,
    startOffsetY: Dp,
    clickedOffsetY: Dp,
    durationMillis: Int = 800,
    hasInnerShadow: Boolean = false,
    modifier: Modifier = Modifier
) {

    val offsetX by animateDpAsState(
        targetValue = if (isClicked) clickedOffsetX else startOffsetX,
        animationSpec = tween(durationMillis = durationMillis, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isClicked) clickedOffsetY else startOffsetY,
        animationSpec = tween(durationMillis = durationMillis, easing = EaseOut)
    )

    val innerShadowColor by animateColorAsState(
        targetValue = if (isClicked) Color(0xFFBFD2FF) else Color.Transparent,
        animationSpec = tween(durationMillis = durationMillis, easing = EaseOut)
    )

    Box(
        modifier = modifier
            .size(size)
            .offset(x = offsetX, y = offsetY)
            .background(color, CircleShape)
            .then(
                if (hasInnerShadow) Modifier.innerShadow(
                    shape = CircleShape,
                    color = innerShadowColor,
                    blur = 4.dp,
                    offsetX = 1.dp,
                    offsetY = 1.dp
                ) else Modifier
            )
    )
}