package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import kotlinx.coroutines.delay

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
) {

    var visibleCircular1 by remember { mutableStateOf(false) }
    var visibleCircular2 by remember { mutableStateOf(false) }
    var visibleCircular3 by remember { mutableStateOf(false) }
    var visibleMessageBox by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        visibleCircular1 = true
        delay(200)
        visibleCircular2 = true
        delay(200)
        visibleCircular3 = true
        delay(200)
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
            Box(
                modifier = Modifier.align(Alignment.BottomEnd),
                contentAlignment = Alignment.BottomEnd
            ) {

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
                        visible = visibleCircular1,
                        enter = scaleIn(animationSpec = tween(800))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .clip(shape = CircleShape)
                                .background(color = Theme.color.surfaceHigh)
                        )
                    }

                    AnimatedVisibility(
                        modifier = Modifier
                            .padding(bottom = 9.dp, end = 5.dp)
                            .align(Alignment.BottomEnd),
                        visible = visibleCircular2,
                        enter = scaleIn(animationSpec = tween(800))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(shape = CircleShape)
                                .background(color = Theme.color.surfaceHigh),
                        )
                    }
                    AnimatedVisibility(
                        modifier = Modifier.align(Alignment.TopStart),
                        visible = visibleCircular3,
                        enter = scaleIn(animationSpec = tween(800))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(shape = CircleShape)
                                .background(color = Theme.color.surfaceHigh)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 21.6.dp, bottom = 11.2.dp)
                        .size(14.4.dp)
                        .clip(shape = CircleShape)
                        .background(color = Theme.color.primary.copy(alpha = 0.16f))
                )

            }

            AnimatedVisibility(
                visible = visibleMessageBox, enter = fadeIn(animationSpec = tween(600))
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 127.dp)
                        .width(203.dp)
                        .height(74.dp)
                        .shadow(
                            elevation = 12.dp, shape = RoundedCornerShape(
                                bottomEnd = 2.dp,
                                bottomStart = 16.dp,
                                topEnd = 16.dp,
                                topStart = 16.dp
                            ), spotColor = Color(0x0A000000)
                        )
                        .clip(
                            shape = RoundedCornerShape(
                                bottomEnd = 2.dp,
                                bottomStart = 16.dp,
                                topEnd = 16.dp,
                                topStart = 16.dp
                            )
                        )
                        .background(color = Theme.color.surfaceHigh)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.no_task_today),
                            style = Theme.textStyle.title.small,
                            color = Theme.color.body,
                            modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                        )
                        Text(
                            text = stringResource(R.string.tap_to_add_task),
                            style = Theme.textStyle.body.small,
                            color = Theme.color.hint,
                            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

