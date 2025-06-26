package com.sanaa.tudee_assistant.presentation.app

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sanaa.tudee_assistant.presentation.designSystem.component.SnackBar
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.SnackBarState
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.HomeScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.LocalAppNavController
import com.sanaa.tudee_assistant.presentation.navigation.MainScreenRoute
import com.sanaa.tudee_assistant.presentation.navigation.OnBoardingScreenRoute
import com.sanaa.tudee_assistant.presentation.screen.categoryTask.CategoryTaskScreen
import com.sanaa.tudee_assistant.presentation.screen.main.MainScreen
import com.sanaa.tudee_assistant.presentation.screen.onBoarding.OnBoardingScreen
import com.sanaa.tudee_assistant.presentation.shared.LocalSnackBarState
import com.sanaa.tudee_assistant.presentation.shared.LocalThemeState
import kotlinx.coroutines.delay

@Composable
fun TudeeApp(isFirstLaunch: Boolean, isDarkTheme: Boolean) {
    val snackBarState = remember { mutableStateOf(SnackBarState()) }

    val view = LocalView.current
    val activity = view.context as? ComponentActivity

    LaunchedEffect(isDarkTheme) {
        val darkIcons = !isDarkTheme

        activity?.window?.also { window ->
            WindowInsetsControllerCompat(window, view).apply {
                isAppearanceLightStatusBars = darkIcons
                isAppearanceLightNavigationBars = darkIcons
            }
        }
    }

    val appNavController = rememberNavController()
    Box {
        CompositionLocalProvider(
            LocalAppNavController provides appNavController,
            LocalSnackBarState provides snackBarState,
            LocalThemeState provides isDarkTheme
        ) {
            AppNavigation(
                startDestination = if (isFirstLaunch) OnBoardingScreenRoute else MainScreenRoute,
                modifier = Modifier
            )
        }

        AnimatedVisibility(
            visible = snackBarState.value.isVisible,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .systemBarsPadding()
                .padding(top = 16.dp),
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            SnackBar(state = snackBarState.value)
        }

        LaunchedEffect(snackBarState.value) {
            if (snackBarState.value.isVisible) {
                delay(3000)
                snackBarState.value = snackBarState.value.copy(isVisible = false)
            }
        }

    }
}


@Composable
private fun AppNavigation(
    startDestination: Any,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = Modifier.background(Theme.color.surface),
        navController = AppNavigation.app,
        startDestination = startDestination
    ) {
        composable<OnBoardingScreenRoute> {
            OnBoardingScreen(modifier = modifier.statusBarsPadding())
        }

        composable<MainScreenRoute> {
            MainScreen(
                startDestination = HomeScreenRoute,
            )
        }

        composable<CategoryTasksScreenRoute> {
            CategoryTaskScreen(
                categoryId = it.toRoute<CategoryTasksScreenRoute>().id,
            )
        }
    }
}