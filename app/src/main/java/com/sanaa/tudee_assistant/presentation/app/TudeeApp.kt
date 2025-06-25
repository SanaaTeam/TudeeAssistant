package com.sanaa.tudee_assistant.presentation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.delay

@Composable
fun TudeeApp(isFirstLaunch: Boolean) {
    var statusBarColor by remember { mutableStateOf(Color.White) }
    val snackBarState = remember { mutableStateOf(SnackBarState()) }
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(containerColor = statusBarColor) { innerPadding ->
            val appNavController = rememberNavController()
            CompositionLocalProvider(
                LocalAppNavController provides appNavController,
                LocalSnackBarState provides snackBarState
            ) {
                AppNavigation(
                    startDestination = if (isFirstLaunch) OnBoardingScreenRoute else MainScreenRoute,
                    modifier = Modifier.padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()
                    ),
                    onChangeStatusBarColor = { color -> statusBarColor = color },
                )
            }
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
    onChangeStatusBarColor: (Color) -> Unit,
) {
    NavHost(
        modifier = Modifier,
        navController = AppNavigation.app,
        startDestination = startDestination
    ) {
        composable<OnBoardingScreenRoute> {
            onChangeStatusBarColor(Theme.color.surface)
            OnBoardingScreen(modifier = modifier)
        }

        composable<MainScreenRoute> {
            MainScreen(
                startDestination = HomeScreenRoute,
                onStatusBarColor = onChangeStatusBarColor,
                modifier = modifier
            )
        }

        composable<CategoryTasksScreenRoute> {
            CategoryTaskScreen(
                categoryId = it.toRoute<CategoryTasksScreenRoute>().id,
                modifier = modifier
            )
        }
    }
}