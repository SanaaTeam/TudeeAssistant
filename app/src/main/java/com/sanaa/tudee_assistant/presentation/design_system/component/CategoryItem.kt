package com.sanaa.tudee_assistant.presentation.design_system.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composables.CategoryCount
import com.sanaa.tudee_assistant.presentation.composables.CheckMarkContainer
import com.sanaa.tudee_assistant.presentation.model.Category
import com.sanaa.tudee_assistant.presentation.model.DefaultCategory
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: () -> Unit,
    topContent: @Composable () -> Unit = {},
) {
    Column(
        modifier = modifier
            .width(104.dp)
            .height(102.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .background(color = Theme.color.surfaceHigh, shape = RoundedCornerShape(100.dp))
                .size(78.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                topContent()
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = category.iconResource),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = category.title,
                style = Theme.textStyle.label.small,
                color = Theme.color.body,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(name = "Dark Theme")
@Composable
fun CategoryDarkPreview() {
    TudeeTheme(isDarkTheme = true) {
        CategoryItem(
            category = Category(DefaultCategory.EDUCATION.name, R.drawable.education_cat),
            onClick = {},
            topContent = { CheckMarkContainer(modifier = Modifier.padding(2.dp)) },
            modifier = Modifier.background(color = Theme.color.surface)

        )
    }
}

@Preview(name = "Light Theme")
@Composable
fun CategoryLightPreview() {
    TudeeTheme(isDarkTheme = false) {
        CategoryItem(
            category = Category(DefaultCategory.EDUCATION.name, R.drawable.education_cat),
            onClick = {},
            topContent = { CategoryCount("16") },
            modifier = Modifier.background(color = Theme.color.surface)

        )
    }
}
