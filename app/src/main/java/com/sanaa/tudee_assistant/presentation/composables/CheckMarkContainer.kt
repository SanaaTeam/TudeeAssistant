package com.sanaa.tudee_assistant.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.Category
import com.sanaa.tudee_assistant.presentation.model.DefaultCategory

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
fun CheckMarkContainerPreview() {
    TudeeTheme(isDarkTheme = false) {
        CheckMarkContainer()
    }
}