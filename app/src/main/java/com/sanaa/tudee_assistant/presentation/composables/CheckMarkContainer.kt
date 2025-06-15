package com.sanaa.tudee_assistant.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme


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

@Preview()
@Composable
private fun CheckMarkContainerPreview() {
    TudeeTheme(isDarkTheme = false) {
        CheckMarkContainer()
    }
}