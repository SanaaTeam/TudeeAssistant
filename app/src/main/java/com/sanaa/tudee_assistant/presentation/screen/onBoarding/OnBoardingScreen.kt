package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import android.app.Activity
import android.view.WindowManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.PreferencesManager
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.route.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.route.OnBoardingScreenRoute
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

data class OnBoardingContent(val title: String, val description: String, val imageRes: Int)

@Composable
fun getOnBoardingContent(page: Int): OnBoardingContent {
    return when (page) {
        0 -> OnBoardingContent(
            title = stringResource(R.string.onboarding_title_0),
            description = stringResource(R.string.onboarding_desc_0),
            imageRes = R.drawable.boarding_screen1
        )

        1 -> OnBoardingContent(
            title = stringResource(R.string.onboarding_title_1),
            description = stringResource(R.string.onboarding_desc_1),
            imageRes = R.drawable.boarding_screen2
        )

        else -> OnBoardingContent(
            title = stringResource(R.string.onboarding_title_2),
            description = stringResource(R.string.onboarding_desc_2),
            imageRes = R.drawable.boarding_screen3
        )
    }
}

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    preferencesManager: PreferencesManager = koinInject<PreferencesManager>(),
) {
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

    val pagerState = rememberPagerState(pageCount = { 3 }, initialPage = 0)
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.color.overlay)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_ellipse),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Theme.dimension.extraLarge),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.fillMaxWidth()) {
                if (pagerState.currentPage != 2) {
                    Text(
                        text = stringResource(R.string.skip),
                        color = Theme.color.primary,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = Theme.dimension.medium, top = Theme.dimension.medium)
                            .clickable {
                                navController.navigate(MainScreenRoute) {
                                    popUpTo(OnBoardingScreenRoute) { inclusive = true }
                                }
                                scope.launch {
                                    preferencesManager.setOnboardingCompleted()
                                }

                            }
                    )
                }
            }

            OnBoardingPager(pagerState = pagerState, preferencesManager, navController)

            PageIndicator(
                currentPage = pagerState.currentPage, pageCount = pagerState.pageCount
            )
        }
    }
}

@Composable
fun OnBoardingPager(
    pagerState: PagerState,
    preferencesManager: PreferencesManager,
    navController: NavController,
) {
    HorizontalPager(
        state = pagerState, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.97f)
    ) { page ->
        val content = getOnBoardingContent(page)

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
        ) {
            DialogContainer(
                title = content.title,
                description = content.description,
                painter = painterResource(id = content.imageRes),
                pagerState = pagerState,
                preferencesManager = preferencesManager,
                navController = navController
            )
        }
    }
}

@Composable
fun PageIndicator(currentPage: Int, pageCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(bottom = Theme.dimension.medium),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { index ->
            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) Theme.color.primary else Theme.color.primaryVariant,
                animationSpec = tween(durationMillis = 100),
                label = ""
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = Theme.dimension.extraSmall)
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
    modifier: Modifier = Modifier,
    preferencesManager: PreferencesManager,
    navController: NavController,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.dimension.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Theme.dimension.extraLarge)
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
                .background(
                    Theme.color.onPrimaryCard,
                    RoundedCornerShape(Theme.dimension.extraLarge)
                )
                .border(
                    1.dp,
                    Theme.color.onPrimaryStroke,
                    RoundedCornerShape(Theme.dimension.extraLarge)
                )
                .padding(horizontal = Theme.dimension.medium)
                .padding(top = Theme.dimension.large, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            Text(
                text = title,
                style = Theme.textStyle.title.medium,
                color = Theme.color.title,
                textAlign = TextAlign.Center,
            )
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
                val currentPage = pagerState.currentPage
                if (currentPage == 2) {
                    coroutineScope.launch {
                        navController.navigate(MainScreenRoute) {
                            popUpTo(OnBoardingScreenRoute) { inclusive = true }
                        }
                        preferencesManager.setOnboardingCompleted()
                    }
                }
                val nextPage = currentPage + 1
                if (nextPage < pagerState.pageCount) {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            },
            iconRes = R.drawable.icon_arrow_right_double,
            modifier = Modifier.offset(y = (-68).dp)
        )
    }
}
//
//@Preview(widthDp = 360)
//@Composable
//private fun BoardingScreenPreview() {
//    TudeeTheme( false) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Theme.color.surface)
//        ) {
//            OnBoardingScreen(
//                navController = rememberNavController(),
//                preferencesManager = preferencesManager
//            )
//        }
//    }
//}