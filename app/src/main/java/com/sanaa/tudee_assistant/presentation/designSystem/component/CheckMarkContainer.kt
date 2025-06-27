package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme


@Composable
fun CheckMarkContainer(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(R.drawable.checkmark_container),
            contentDescription = null,
        )
    }
}

@PreviewLightDark
@Composable
private fun CheckMarkContainerPreview() {
    TudeeTheme(isSystemInDarkTheme()) {
        CheckMarkContainer()
    }
}