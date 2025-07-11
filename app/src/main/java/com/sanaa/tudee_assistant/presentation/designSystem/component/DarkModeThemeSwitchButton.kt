package com.sanaa.tudee_assistant.presentation.designSystem.component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.moonColor
import com.sanaa.tudee_assistant.presentation.designSystem.theme.sunColor
import com.sanaa.tudee_assistant.presentation.modifire.dropShadow
import com.sanaa.tudee_assistant.presentation.modifire.innerShadow

@Composable
fun DarkModeThemeSwitchButton(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {},
) {


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

        FirstGreyCloud(isDarkMode, animationSpecDurationMillis)

        SecondGreyCloud(isDarkMode, animationSpecDurationMillis)

        FirstWhiteCloud(isDarkMode, animationSpecDurationMillis)

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
                        .size(Theme.dimension.extraLarge)
                        .align(Alignment.CenterEnd)
                ) {
                    MoonLargeCircle()
                    MoonSmallCircle()
                }
            }
        }
    }
}


@Composable
private fun BoxScope.AnimatedMoon(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {
    val correctLayoutDirection = LocalLayoutDirection.current
    AnimatedVisibility(
        isDarkMode,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        exit = slideOutHorizontally(

            targetOffsetX = { if (correctLayoutDirection == LayoutDirection.Rtl) it else -it },
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

        Box(
            modifier = Modifier
                .size(Theme.dimension.extraLarge)
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
private fun BoxScope.AnimatedSun(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {
    val correctLayoutDirection = LocalLayoutDirection.current
    AnimatedVisibility(
        !isDarkMode,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = animationSpecDurationMillis,
                easing = EaseOut
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { if (correctLayoutDirection == LayoutDirection.Rtl) -it else it },
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

        Box(
            modifier = Modifier
                .size(Theme.dimension.extraLarge)
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
private fun BoxScope.MoonSmallCircle() {
    Box(
        modifier = Modifier
            .size(Theme.dimension.extraSmall)
            .align(Alignment.BottomEnd)
            .offset(x = (-9).dp, y = (-4).dp)
            .clip(CircleShape)
            .align(Alignment.BottomEnd)
            .background(Color(0xFFE9EFFF))
            .innerShadow(
                shape = CircleShape,
                color = Color(0xFFBFD2FF),
                blur = Theme.dimension.extraSmall,
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
            .offset(x = Theme.dimension.extraSmall, y = (-6).dp)
            .clip(CircleShape)
            .background(Color(0xFFE9EFFF))
            .innerShadow(
                shape = CircleShape,
                color = Color(0xFFBFD2FF),
                blur = Theme.dimension.extraSmall,
                offsetX = 1.dp,
                offsetY = 1.dp
            )
    )
}

@Composable
private fun BoxScope.TransformingWhiteCloud(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {
    AnimatedVisibility(
        !isDarkMode,
        enter = slideIn(
            initialOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2)) },
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
            targetOffset = { IntOffset((-1.5 * it.width).toInt(), (it.height / 2)) },
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
            .offset(x = (-12).dp, y = Theme.dimension.extraSmall)
    ) {
        Box(
            modifier = Modifier
                .size(14.dp, Theme.dimension.medium)
                .background(Color.White, RoundedCornerShape(100.dp))

        )
    }
}

@Composable
private fun BoxScope.FirstWhiteCloud(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.BottomEnd),
        size = Theme.dimension.medium,
        startOffsetX = 1.dp,
        clickedOffsetX = 50.dp,
        startOffsetY = Theme.dimension.extraSmall,
        clickedOffsetY = 50.dp,
        animationSpecDurationMillis = animationSpecDurationMillis
    )
}

@Composable
private fun BoxScope.SecondGreyCloud(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.BottomEnd),
        size = Theme.dimension.large,
        startOffsetX = (-6).dp,
        clickedOffsetX = 50.dp,
        startOffsetY = Theme.dimension.small,
        clickedOffsetY = 50.dp,
        color = Color(0xFFF0F0F0),
        animationSpecDurationMillis = animationSpecDurationMillis
    )
}

@Composable
private fun BoxScope.FirstGreyCloud(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int
) {
    AnimatedCircle(
        isClicked = isDarkMode,
        modifier = Modifier.align(Alignment.TopEnd),
        size = Theme.dimension.extraLarge,
        startOffsetX = 14.dp,
        clickedOffsetX = 50.dp,
        startOffsetY = (-4).dp,
        clickedOffsetY = 50.dp,
        color = Color(0xFFF0F0F0),
        animationSpecDurationMillis = animationSpecDurationMillis
    )
}

@Composable
private fun BoxScope.AnimatedTransformingMoonCircle(
    isDarkMode: Boolean,
    animationSpecDurationMillis: Int,
) {

    val moonCircleSize by animateDpAsState(
        targetValue = if (isDarkMode) Theme.dimension.small else 29.dp,
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
        clickedOffsetY = Theme.dimension.extraSmall,
        color = circleColor,
        hasInnerShadow = true,
        animationSpecDurationMillis = animationSpecDurationMillis
    )
}

@Composable
private fun AnimatedCircle(
    size: Dp,
    startOffsetX: Dp,
    clickedOffsetX: Dp,
    startOffsetY: Dp,
    clickedOffsetY: Dp,
    animationSpecDurationMillis: Int,
    modifier: Modifier = Modifier,
    isClicked: Boolean = false,
    color: Color = Color.White,
    hasInnerShadow: Boolean = false,
) {

    val offsetX by animateDpAsState(
        targetValue = if (isClicked) clickedOffsetX else startOffsetX,
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
    )

    val offsetY by animateDpAsState(
        targetValue = if (isClicked) clickedOffsetY else startOffsetY,
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
    )

    val innerShadowColor by animateColorAsState(
        targetValue = if (isClicked) Color(0xFFBFD2FF) else Color.Transparent,
        animationSpec = tween(durationMillis = animationSpecDurationMillis, easing = EaseOut)
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
                    blur = Theme.dimension.extraSmall,
                    offsetX = 1.dp,
                    offsetY = 1.dp
                ) else Modifier
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun DarkModeThemeSwitchButtonPreview() {
    Column {

        var checkedState by remember { mutableStateOf(false) }

        DarkModeThemeSwitchButton(
            checkedState,
            800,
            Modifier.padding(Theme.dimension.extraSmall)
        ) { checkedState = !checkedState }
        DarkModeThemeSwitchButton(
            !checkedState,
            800,
            Modifier.padding(Theme.dimension.extraSmall)
        ) { checkedState = !checkedState }
    }
}