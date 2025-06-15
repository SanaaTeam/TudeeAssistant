package com.sanaa.tudee_assistant.presentation.design_system.component

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
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.theme.TudeeTheme
import com.sanaa.tudee_assistant.presentation.model.CategoryTaskState
import com.sanaa.tudee_assistant.presentation.model.Priority

@Composable
fun CategoryTaskCard(
    categoryTask: CategoryTaskState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(111.dp)
            .clip(RoundedCornerShape(Theme.dimension.medium))
            .background(Theme.color.surfaceHigh)
            .padding(
                start = Theme.dimension.extraSmall,
                top = Theme.dimension.extraSmall,
                end = Theme.dimension.regular
            ),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.size(Theme.dimension.extraLarge),
                    painter = categoryTask.icon,
                    contentDescription = null,
                )
            }

            Row {

                categoryTask.date?.let { DateChip(it) }

                PriorityTag(
                    modifier = Modifier.padding(start = Theme.dimension.extraSmall),
                    priority = categoryTask.priority
                )
            }
        }


        Column(
            modifier = Modifier.padding(start = Theme.dimension.small),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = categoryTask.title,
                color = Theme.color.body,
                style = Theme.textStyle.label.large,
                maxLines = 1,
            )

            categoryTask.description?.let {
                Text(
                    text = categoryTask.description,
                    color = Theme.color.hint,
                    style = Theme.textStyle.label.small,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun DateChip(date: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(Theme.color.surface)
            .padding(vertical = 6.dp, horizontal = Theme.dimension.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Image(
            modifier = Modifier.size(Theme.dimension.regular),
            painter = painterResource(id = R.drawable.calendar_favorite_01),
            contentDescription = null,
        )

        Text(
            text = date,
            color = Theme.color.body,
            style = Theme.textStyle.label.small
        )
    }
}

@Preview(widthDp = 360)
@Composable
private fun Preview() {
    TudeeTheme {
        val items = listOf(
            CategoryTaskState(
                icon = painterResource(R.drawable.birthday_cake),
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                date = null,
                priority = Priority.MEDIUM
            ),
            CategoryTaskState(
                icon = painterResource(R.drawable.birthday_cake),
                title = "Organize Study Desk",
                date = "12-03-2025",
                priority = Priority.LOW
            ),
            CategoryTaskState(
                icon = painterResource(R.drawable.birthday_cake),
                title = "Organize Study Desk",
                description = "Review cell structure and functions for tomorrow...",
                date = "12-03-2025",
                priority = Priority.HIGH
            ),
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surface),
            contentPadding = PaddingValues(Theme.dimension.medium),
            verticalArrangement = Arrangement.spacedBy(Theme.dimension.medium)
        ) {
            items(
                items
            ) { CategoryTaskCard(it) }
        }
    }
}