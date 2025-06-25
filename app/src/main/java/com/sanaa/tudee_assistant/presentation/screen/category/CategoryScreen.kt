package com.sanaa.tudee_assistant.presentation.screen.category

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryCount
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.SnackBar
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.CategoryTasksScreenRoute
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = koinViewModel<CategoryViewModel>(),
) {
    val state by viewModel.state.collectAsState()
    CategoryScreenContent(
        modifier = modifier,
        state = state,
        listener = viewModel
    )
}

@Composable
fun CategoryScreenContent(
    modifier: Modifier = Modifier,
    state: CategoryScreenUiState,
    listener: CategoryInteractionListener,
) {
    val screenNavController = AppNavigation.app
    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            delay(3000)
            listener.onHideSnakeBar()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Theme.color.surfaceHigh)
    ) {
        Column {
            AppBar()

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 104.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Theme.color.surface),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.allCategories) { category ->
                    CategoryItem(
                        category = category,
                        topContent = { CategoryCount(category.tasksCount.toString()) },
                        onClick = {
                            screenNavController.navigate(CategoryTasksScreenRoute(category.id))
                        },
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(vertical = 10.dp, horizontal = Theme.dimension.regular),
            iconRes = R.drawable.resources_add,
        ) {
            listener.onToggleAddCategorySheet(true)
        }


        AnimatedVisibility(
            visible = state.snackBarState.isVisible,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter),
            enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut()
        ) {
            SnackBar(state = state.snackBarState)
        }
    }

    if (state.isAddCategorySheetVisible) {
        AddEditCategoryBottomSheet(
            category = state.newCategory,
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
            isFormValid = { listener.isFormValid() }
        )
    }
}

@Composable
private fun AppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = Theme.color.surfaceHigh)
    ) {
        Text(
            stringResource(R.string.categories),
            style = Theme.textStyle.title.large,
            color = Theme.color.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        )
    }
}