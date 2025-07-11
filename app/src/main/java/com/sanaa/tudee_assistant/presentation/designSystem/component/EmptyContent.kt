package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.no_task_today),
    caption: String? = stringResource(R.string.tap_to_add_task),
) {

    var visibleCircular1 by rememberSaveable { mutableStateOf(false) }
    var visibleCircular2 by rememberSaveable { mutableStateOf(false) }
    var visibleCircular3 by rememberSaveable { mutableStateOf(false) }
    var visibleMessageBox by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visibleCircular1 = true
        visibleCircular2 = true
        visibleCircular3 = true
        visibleMessageBox = true
    }

    Box(
        modifier = modifier.width(330.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .height(160.dp)
                .align(Alignment.CenterEnd)
                .background(color = Color.Transparent),
        ) {
            CircularContainer(
                modifier = Modifier.align(Alignment.BottomEnd),
                listOf(visibleCircular1, visibleCircular2, visibleCircular3)
            )

            AnimatedVisibility(
                visible = visibleMessageBox, enter = fadeIn(animationSpec = tween(300))
            ) {
                MessageBox(
                    modifier = Modifier.align(Alignment.TopEnd), title = title, caption = caption
                )
            }
        }
    }
}

@Composable
private fun CircularContainer(modifier: Modifier = Modifier, visibleCirculars: List<Boolean>) {
    Box(modifier = modifier, contentAlignment = Alignment.BottomEnd) {

        Box(
            modifier = Modifier
                .padding(end = 5.dp, bottom = 3.dp)
                .size(144.dp)
                .border(
                    color = Theme.color.primary.copy(alpha = 0.16f),
                    shape = CircleShape,
                    width = 1.dp
                )
        )

        Box(
            modifier = Modifier
                .size(136.dp)
                .clip(CircleShape)
                .background(color = Theme.color.primary.copy(alpha = 0.16f))
        )

        Image(
            painter = painterResource(R.drawable.robot),
            contentDescription = null,
            modifier = Modifier
                .size(width = 107.dp, height = 100.dp)
                .padding(bottom = 3.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 19.dp, top = 17.dp)
                .width(23.dp)
                .height(34.dp)
        ) {
            AnimatedVisibility(
                modifier = Modifier.align(Alignment.BottomEnd),
                visible = visibleCirculars[0],
                enter = scaleIn(animationSpec = tween(100))
            ) {
                Box(
                    modifier = Modifier
                        .size(Theme.dimension.extraSmall)
                        .clip(CircleShape)
                        .background(Theme.color.surfaceHigh)
                )
            }

            AnimatedVisibility(
                modifier = Modifier
                    .padding(bottom = 9.dp, end = 5.dp)
                    .align(Alignment.BottomEnd),
                visible = visibleCirculars[1],
                enter = scaleIn(animationSpec = tween(200))
            ) {
                Box(
                    modifier = Modifier
                        .size(Theme.dimension.small)
                        .clip(CircleShape)
                        .background(Theme.color.surfaceHigh)
                )
            }

            AnimatedVisibility(
                modifier = Modifier.align(Alignment.TopStart),
                visible = visibleCirculars[2],
                enter = scaleIn(animationSpec = tween(300))
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Theme.color.surfaceHigh)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 21.6.dp, bottom = 11.2.dp)
                .size(14.4.dp)
                .clip(CircleShape)
                .background(Theme.color.primary.copy(alpha = 0.16f))
        )
    }
}

@Composable
private fun MessageBox(
    title: String, caption: String?, modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .padding(end = 127.dp)
            .fillMaxWidth()
            .height(74.dp)
            .shadow(
                elevation = Theme.dimension.regular, shape = RoundedCornerShape(
                    bottomEnd = 2.dp,
                    bottomStart = Theme.dimension.medium,
                    topEnd = Theme.dimension.medium,
                    topStart = Theme.dimension.medium
                ), spotColor = Color(0x0A000000)
            )
            .clip(
                shape = RoundedCornerShape(
                    bottomEnd = 2.dp,
                    bottomStart = Theme.dimension.medium,
                    topEnd = Theme.dimension.medium,
                    topStart = Theme.dimension.medium
                )
            )
            .background(Theme.color.surfaceHigh)
            .padding(end = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = Theme.textStyle.title.small,
                color = Theme.color.body,
                modifier = Modifier.padding(
                    start = Theme.dimension.regular, top = Theme.dimension.small
                )
            )
            if (caption != null) Text(
                text = caption,
                style = Theme.textStyle.body.small,
                color = Theme.color.hint,
                modifier = Modifier.padding(
                    start = Theme.dimension.regular, top = Theme.dimension.extraSmall
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewEmptyScreen() {
    TudeeTheme(isDark = isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentAlignment = Alignment.Center
        ) {
            EmptyContent()
        }
    }
}
