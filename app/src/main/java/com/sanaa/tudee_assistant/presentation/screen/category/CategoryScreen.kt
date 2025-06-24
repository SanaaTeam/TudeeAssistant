package com.sanaa.tudee_assistant.presentation.screen.category

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryCount
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.route.CategoryTasksScreenRoute
import org.koin.androidx.compose.koinViewModel


@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = koinViewModel<CategoryViewModel>(),
    screenNavController: NavHostController,

    ) {
    val state by viewModel.state.collectAsState()
    CategoryScreenContent(
        modifier = modifier,
        state = state,
        listener = viewModel,
        onShowTasksByCategoryClick = { categoryId ->
            screenNavController.navigate(CategoryTasksScreenRoute(categoryId))
        }
    )
}
@Composable
fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    state: CategoryScreenUiState,
    listener: CategoryInteractionListener,
    onShowTasksByCategoryClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.color.surfaceHigh)
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

                items(state.allCategories) { category ->
                    CategoryItem(
                        category = category,
                        onClick = {
                            onShowTasksByCategoryClick(category.id)
                        },
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
                        listener.onToggleAddCategorySheet(true)
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
    if (state.isAddCategorySheetVisible) {
        AddEditCategoryBottomSheet(
            category = state.currentCategory,
            isEditMode = false,
            onTitleChange = { listener.onCategoryTitleChange(it) },
            onImageSelected = { listener.onCategoryImageSelected(it) },
            onSaveClick = {
                listener.onAddCategory(it)
                listener.onToggleAddCategorySheet(false)
            },
            onDismiss = {
                listener.onToggleAddCategorySheet(false)
            },
            isFormValid = { state.categoryTitle.isNotBlank() }
        )
    }
}
