package com.sanaa.tudee_assistant.screen

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(modifier: Modifier = Modifier) {
    val isInPreview = LocalView.current.isInEditMode

    if (!isInPreview) {
        val window = LocalView.current.context as Activity
        LaunchedEffect(Unit) {
            window.window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.overlay)
    ) {
        Image(
            painter = painterResource(id = R.drawable.boarding_screen000),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )

        val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0)

        if (pagerState.currentPage != 2) {
            Text(
                text = stringResource(R.string.skip),
                color = Theme.color.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp)
                    .systemBarsPadding()
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    when (it) {
                        0 -> DialogContainer(
                            title = "Overwhelmed with tasks?",
                            description = "Let’s bring some order to the chaos.\nTudee is here to help you sort, plan, and\nbreathe easier.",
                            painter = painterResource(id = R.drawable.boarding_screen1),
                            pagerState = pagerState

                        )

                        1 -> DialogContainer(
                            title = "Uh-oh! Procrastinating again",
                            description = "Tudee not mad… just a little \ndisappointed, Let’s get back on track \ntogether.",
                            painter = painterResource(id = R.drawable.boarding_screen2),
                            pagerState = pagerState

                        )

                        2 -> DialogContainer(
                            title = "Let’s complete task and celebrate\ntogether.",
                            description = "Progress is progress. Tudee will celebrate you on for every win, big or small.",
                            painter = painterResource(id = R.drawable.boarding_screen3),
                            pagerState = pagerState

                        )
                    }
                }
            }
            PageIndicator(currentPage = pagerState.currentPage, pageCount = pagerState.pageCount)
        }
    }
}

@Composable
fun PageIndicator(currentPage: Int, pageCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) Theme.color.primary else Color(0xFFEFF9FE),
                animationSpec = tween(durationMillis = 100),
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 24.dp)
                    .size(width = 100.dp, height = 5.dp)
                    .background(animatedColor, shape = RoundedCornerShape(2.dp))
            )
        }
    }
}

@Composable
fun DialogContainer(
    title: String,
    description: String,
    painter: Painter,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(width = 296.dp, height = 260.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(192.dp)
                .background(Theme.color.onPrimaryCard, RoundedCornerShape(32.dp))
                .border(1.dp, Theme.color.onPrimaryStroke, RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.color.title,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                style = Theme.textStyle.body.medium,
                color = Theme.color.body,
                textAlign = TextAlign.Center,
            )
        }

        val coroutineScope = rememberCoroutineScope()
        FloatingActionButton(
            enabled = true,
            isLoading = false,
            onClick = {
                val nextPage = pagerState.currentPage + 1
                if (nextPage < pagerState.pageCount) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            },
            iconRes = R.drawable.icon_arrow_right_double,
            modifier = Modifier.offset(y = (-70).dp)
        )
    }
}

@Preview(widthDp = 360)
@Composable
private fun BoardingScreenPreview() {
    TudeeTheme(isDarkTheme = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
        ) {
            OnBoardingScreen()
        }
    }
}