package com.sanaa.tudee_assistant.presentation.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.presentation.composable.CategoryThumbnail
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.designSystem.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.state.CategoryUiState
import com.sanaa.tudee_assistant.presentation.utils.DataProvider

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: CategoryUiState,
    onClick: () -> Unit,
    topContent: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .height(102.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .background(color = Theme.color.surfaceHigh, shape = RoundedCornerShape(100.dp))
                .size(78.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center,
            ) {
                CategoryThumbnail(modifier = Modifier.size(32.dp), imagePath = category.imagePath)
            }
            Box(
                modifier = Modifier.align(Alignment.TopEnd),
            ) {
                topContent()
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = if (category.isDefault) DataProvider.getStringResourceByName(
                    category.name,
                    LocalContext.current
                ) else category.name,
                style = Theme.textStyle.label.small,
                color = Theme.color.body,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(name = "Dark Theme")
@Composable
private fun CategoryDarkPreview() {
    TudeeTheme(true) {
        CategoryItem(
            category = CategoryUiState(
                id = 0,
                name = "Education",
                imagePath = "file:///android_asset/categories/education.png",
                isDefault = true,
                tasksCount = 10
            ),
            onClick = {},
            topContent = { CheckMarkContainer(modifier = Modifier.padding(2.dp)) },
            modifier = Modifier.background(color = Theme.color.surface)

        )
    }
}

@Preview(name = "Light Theme", locale = "ar")
@Composable
private fun CategoryLightPreview() {
    TudeeTheme(false) {
        CategoryItem(
            category = CategoryUiState(
                id = 0,
                name = "Adoration",
                imagePath = "file:///android_asset/categories/education.png",
                isDefault = true,
                tasksCount = 10
            ),
            onClick = {},
            topContent = { CategoryCount("16") },
            modifier = Modifier.background(color = Theme.color.surface)

        )
    }
}
