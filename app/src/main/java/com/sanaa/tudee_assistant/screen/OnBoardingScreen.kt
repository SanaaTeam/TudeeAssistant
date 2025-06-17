package com.sanaa.tudee_assistant.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Nunito
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme


@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.overlay)
    )

    {

        Box(
            modifier = Modifier.fillMaxSize()

        ) {
            Image(
                painter = painterResource(id = R.drawable.boarding_screen000),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .size(614.dp, 584.dp)
            )

        }
        Text(
            text = stringResource(R.string.skip),
            fontSize = 18.sp,
            fontFamily = Nunito,
            color = Theme.color.primary,
            lineHeight = 19.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 52.dp)

        )
        val pagerState = rememberPagerState(
            pageCount = { 3 },
            initialPage = 0,

            )
        HorizontalPager(

            state = pagerState,

            ) {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                when (it) {
                    0 -> DialogContainer(
                        title = "Overwhelmed with tasks?",
                        description = "Let’s bring some order to the chaos.\nTudee is here to help you sort, plan, and\nbreathe easier.",
                        painter = painterResource(id = R.drawable.boarding_screen1)
                    )

                    1 -> DialogContainer(
                        title = "Overwhelmed with tasks?",
                        description = "Let’s bring some order to the chaos.\nTudee is here to help you sort, plan, and\nbreathe easier.",
                        painter = painterResource(id = R.drawable.boarding_screen2)
                    )

                    2 -> DialogContainer(
                        title = "Overwhelmed with tasks?",
                        description = "Let’s bring some order to the chaos.\nTudee is here to help you sort, plan, and\nbreathe easier.",
                        painter = painterResource(id = R.drawable.boarding_screen3)
                    )

                }
            }
        }

    }
}

@Preview()
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

@Composable
fun DialogContainer(
    title: String, description: String, painter: Painter,

    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),


        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp)
    )

    {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(width = 296.dp, height = 250.dp)
        )

        Column(
            modifier = Modifier
                .background(Theme.color.onPrimaryCard, RoundedCornerShape(32.dp))
                .border(1.dp, Theme.color.onPrimaryStroke, RoundedCornerShape(32.dp))
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.color.title,
            )
            Text(
                text = description,
                color = Theme.color.body,
                style = Theme.textStyle.body.medium,
                textAlign = TextAlign.Center,
            )
        }
    }
}
