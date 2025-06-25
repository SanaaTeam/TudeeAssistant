package com.sanaa.tudee_assistant.presentation.screen.categoryTask.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.SnackBarState

@Composable
fun CategoryTaskScreenContainer(
    topBar: @Composable () -> Unit,
//    snackBar: @Composable () -> Unit,
//    snackBarState: SnackBarState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = Theme.color.surface)
            .fillMaxWidth()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            topBar()
            content()
        }
//        AnimatedVisibility(
//            visible = snackBarState.isVisible,
//            modifier = Modifier
//                .align(Alignment.TopCenter),
//            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
//            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
//        ) {
//            snackBar()
//        }
    }
}