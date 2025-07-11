package com.sanaa.tudee_assistant.presentation.screen.onBoarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.OnBoardingPageContentItem
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.OnBoardingScreenRoute
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnBoardingViewModel = koinViewModel<OnBoardingViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val navController = AppNavigation.app

    OnBoardingScreenContent(
        state = state,
        interactionListener = viewModel,
        modifier = modifier,
    )

    LaunchedEffect(state.isSkipable) {
        if (state.isSkipable) {
            navController.navigate(MainScreenRoute) {
                popUpTo(OnBoardingScreenRoute) { inclusive = true }
            }
        }
    }
}

@Composable
private fun OnBoardingScreenContent(
    state: OnBoardingScreenUiState,
    interactionListener: OnBoardingScreenInteractionListener,
    modifier: Modifier = Modifier,
) {


    val pagerState = rememberPagerState(
        pageCount = { state.pageList.size },
        initialPage = state.currentPageIndex
    )

    LaunchedEffect(state.currentPageIndex) {
        if (pagerState.currentPage != state.currentPageIndex) {
            pagerState.animateScrollToPage(state.currentPageIndex)
        }
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { interactionListener.setCurrentPage(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.color.overlay)
    ) {

        Image(
            painter = painterResource(id = if (isSystemInDarkTheme()) R.drawable.ellipse_background_dark else R.drawable.ellipse_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .blur(5.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            ) {
                if (state.pageList.isNotEmpty()) {

                    OnBoardingPager(
                        pagerState = pagerState,
                        pageList = state.pageList,
                    )
                    DialogContainer(
                        pageContent = state.pageList[pagerState.currentPage],
                        onNextPageClick = { interactionListener.onNextPageClick() },
                        modifier = Modifier.padding(horizontal = Theme.dimension.medium)
                    )

                    PageIndicator(
                        currentPage = pagerState.currentPage,
                        pageCount = pagerState.pageCount,
                        onIndicatorClick = { index -> interactionListener.setCurrentPage(index) },
                        modifier = Modifier
                            .padding(horizontal = Theme.dimension.medium)
                            .padding(bottom = 24.dp)
                    )
                }
            }
        }
        if (pagerState.currentPage != state.pageList.lastIndex) {
            Box(modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.skip),
                    color = Theme.color.primary,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .clickable { interactionListener.onSkipClick() }
                )
            }
        }
    }
}

@Composable
private fun OnBoardingPager(
    pagerState: PagerState,
    pageList: List<OnBoardingPageContentItem>,
    modifier: Modifier = Modifier,
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxWidth()
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .offset(y = 11.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(pageList[page].imageRes),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.height(260.dp)
            )
        }

    }
}


@Composable
private fun DialogContainer(
    pageContent: OnBoardingPageContentItem,
    onNextPageClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
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
            verticalArrangement = Arrangement.spacedBy(38.dp)
        ) {
            Text(
                text = stringResource(pageContent.title),
                style = Theme.textStyle.title.medium,
                color = Theme.color.title,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(pageContent.description),
                style = Theme.textStyle.body.medium,
                color = Theme.color.body,
                textAlign = TextAlign.Center,
            )
        }

        FloatingActionButton(
            enabled = true,
            isLoading = false,
            onClick = { onNextPageClick() },
            iconRes = R.drawable.icon_arrow_right_double,
            modifier = Modifier.offset(y = (-37).dp)
        )
    }
}

@Composable
private fun PageIndicator(
    currentPage: Int,
    pageCount: Int,
    onIndicatorClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(pageCount) { index ->
            val animatedColor by animateColorAsState(
                targetValue = if (index == currentPage) Theme.color.primary else Theme.color.primaryVariant,
                animationSpec = tween(durationMillis = 100),
            )

            Box(
                modifier = Modifier
                    .height(5.dp)
                    .weight(1f)
                    .background(animatedColor, shape = RoundedCornerShape(100.dp))
                    .clickable { onIndicatorClick(index) }
            )
        }
    }
}

@Preview(widthDp = 360, heightDp = 800)
@Preview(widthDp = 360, heightDp = 680, locale = "ar")
@PreviewScreenSizes
@Composable
private fun BoardingScreenPreview() {

    val pages = listOf(
        OnBoardingPageContentItem(
            title = R.string.onboarding_title_0,
            description = R.string.onboarding_desc_0,
            imageRes = R.drawable.robot_onboarding_page0
        ),
        OnBoardingPageContentItem(
            title = R.string.onboarding_title_1,
            description = R.string.onboarding_desc_1,
            imageRes = R.drawable.robot_onboarding_page1
        ),
        OnBoardingPageContentItem(
            title = R.string.onboarding_title_2,
            description = R.string.onboarding_desc_2,
            imageRes = R.drawable.robot_onboarding_page2
        ),
    )


    val isDarkTheme = false

    var state by remember {
        mutableStateOf(
            OnBoardingScreenUiState(
                pageList = pages,
                currentPageIndex = 0
            )
        )
    }
    val previewActions = object : OnBoardingScreenInteractionListener {
        override fun onNextPageClick() {
            state = state.copy(currentPageIndex = state.currentPageIndex + 1)
        }

        override fun onSkipClick() {}

        override fun setCurrentPage(pageIndex: Int) {
            state = state.copy(currentPageIndex = pageIndex)
        }
    }

    TudeeTheme(isDark = isDarkTheme) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface)
        ) {
            OnBoardingScreenContent(
                state = state,
                interactionListener = previewActions,
            )
        }
    }
}
