package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.component.CategoryThumbnail
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme

@Composable
fun CategoryImageContainer(
    modifier: Modifier = Modifier, content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .background(color = Theme.color.surfaceHigh, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun PreviewCategoryImage() {
    TudeeTheme(isSystemInDarkTheme()) {
        CategoryImageContainer {
            CategoryThumbnail(
                modifier = Modifier.size(32.dp),
                imagePath = "file:///android_asset/categories/adoration.png"
            )
        }
    }
}