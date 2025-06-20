package com.sanaa.tudee_assistant.presentation.screen.category

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = koinViewModel<CategoryViewModel>()
) {
    val state by viewModel.state.collectAsState()
    val showBottomSheet = remember { mutableStateOf(false) }

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
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.color.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(state.currentDateCategory) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {},
                        // condition
                        topContent = { CategoryCount(category.tasksCount.toString()) }
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
                    .clickable {
                        showBottomSheet.value = true
                    },
                contentAlignment = Alignment.Center
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

    if (showBottomSheet.value) {
        AddNewCategory(
            onAddClick = { title, imageUri ->
                val newCategory = CategoryUiModel(
                    name = title,
                    imagePath = imageUri.toString(),
                    isDefault = false,
                    tasksCount = 0
                )
                viewModel.addNewCategory(newCategory)
                showBottomSheet.value = false
            },
            onDismiss = {
                showBottomSheet.value = false
            },
            onImageSelected = { uri -> }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CategoryScreenPreview() {
    CategoryScreen()
}