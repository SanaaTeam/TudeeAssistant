package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun CategoryImageContainer(
    modifier: Modifier = Modifier, painter: Painter
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .background(color = Theme.color.surfaceHigh, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryImage() {
    TudeeTheme {
        CategoryImageContainer(painter = painterResource(id = R.drawable.birthday_cake))
    }
}