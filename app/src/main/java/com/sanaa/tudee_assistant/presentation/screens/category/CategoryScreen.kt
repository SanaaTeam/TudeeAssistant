package com.sanaa.tudee_assistant.presentation.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryCount
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.model.CategoryState

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    //viewModel: CategoryViewModel = getViewModel()
) {
    //val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.color.surfaceHigh)
            .systemBarsPadding()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(color = Theme.color.surfaceHigh)
            ) {
                Text(
                    "Categories",
                    style = Theme.textStyle.title.large,
                    color = Theme.color.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.color.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                val categories = listOf(
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed",
                    "ahmed",
                    "mohamed"
                )

                items(categories) { category ->
                    CategoryItem(
                        category = CategoryState(
                            category,
                            painterResource(R.drawable.education_cat),
                            Theme.color.purpleAccent
                        ),
                        onClick = {},
                        topContent = { CategoryCount("16") } // CheckMarkContainer(modifier = Modifier.padding(2.dp))
                    )
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 12.dp, bottom = 17.dp)
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Theme.color.primaryGradientStart, Theme.color.primaryGradientEnd
                            )
                        )
                    )
                    .clickable { }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(R.drawable.resources_add),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        }
    }
}


@Preview
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen()
}