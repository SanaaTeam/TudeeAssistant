package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.DefaultCategory
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme


@Composable
fun Category(
    category: Category,
    count: String,
    selected: Boolean = false
) {
    Column(
        modifier = Modifier
            .width(104.dp)
            .height(102.dp)
            .background(color = Theme.color.surfaceHigh)
    ) {
        if (!selected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 13.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = Theme.color.surfaceLow,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .padding(vertical = 2.dp, horizontal = 10.5.dp)
                ) {
                    Text(
                        text = count,
                        style = Theme.textStyle.label.small,
                        color = Theme.color.hint
                    )
                }
            }
        }

        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp, top = 2.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(R.drawable.checkmark_container),
                    contentDescription = null,
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = category.iconResource),
                contentDescription = null,
            )
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = category.title,
                style = Theme.textStyle.label.small,
                color = Theme.color.hint,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

@Preview(name = "Dark Theme")
@Composable
fun CategoryDarkPreview() {
    TudeeTheme(isDarkTheme = true) {
        Category(
            category = DefaultCategory.EDUCATION,
            count = "16"
        )
    }
}

@Preview(name = "Light Theme")
@Composable
fun CategoryLightPreview() {
    TudeeTheme(isDarkTheme = false) {
        Category(
            category = DefaultCategory.EDUCATION,
            count = "16",
            selected = true
        )
    }
}
