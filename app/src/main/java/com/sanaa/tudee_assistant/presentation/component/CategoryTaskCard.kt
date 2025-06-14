package com.sanaa.tudee_assistant.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.composables.HorizontalSpace
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.CategoryTask
import com.sanaa.tudee_assistant.presentation.model.Priority

@Composable
fun CategoryTaskCard(
    categoryTask: CategoryTask,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(111.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.surfaceHigh)
            .padding(vertical = 4.dp, horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier
                        .size(32.dp),
                    painter = categoryTask.icon,
                    contentDescription = null,
                )
            }

            HorizontalSpace()

            categoryTask.date?.let { Date(it) }

            HorizontalSpace(4.dp)

            PriorityTag(
                modifier = Modifier,
                priority = categoryTask.priority
            )
        }

        Text(
            modifier = Modifier,
            text = categoryTask.title,
            color = Theme.color.body,
            style = Theme.textStyle.label.large,
            maxLines = 1,
        )

        Text(
            modifier = Modifier,
            text = categoryTask.description,
            color = Theme.color.hint,
            style = Theme.textStyle.label.small,
            maxLines = 1,
        )
    }
}

@Composable
private fun Date(date: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Theme.color.surface)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(12.dp),
            painter = painterResource(id = R.drawable.calendar_favorite_01),
            contentDescription = null,
        )

        HorizontalSpace(2.dp)

        Text(
            modifier = Modifier,
            text = date,
            color = Theme.color.body,
            style = Theme.textStyle.label.small
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TudeeTheme {
        val items = listOf(
            CategoryTask(
                icon = painterResource(R.drawable.birthday_cake),
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                date = null,
                priority = Priority.Medium
            ),
            CategoryTask(
                icon = painterResource(R.drawable.birthday_cake),
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                date = "12-03-2025",
                priority = Priority.Medium
            ),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                items
            ) { CategoryTaskCard(it) }
        }
    }
}