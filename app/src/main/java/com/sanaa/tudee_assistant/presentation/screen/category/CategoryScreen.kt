package com.sanaa.tudee_assistant.presentation.screen.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.component.TextAppBar
import com.sanaa.tudee_assistant.presentation.component.TudeeScaffold
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryCount
import com.sanaa.tudee_assistant.presentation.designSystem.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.designSystem.component.button.FloatingActionButton
import com.sanaa.tudee_assistant.presentation.designSystem.theme.Theme
import com.sanaa.tudee_assistant.presentation.navigation.AppNavigation
import com.sanaa.tudee_assistant.presentation.navigation.CategoryTasksScreenRoute
import com.sanaa.tudee_assistant.presentation.shared.LocalSnackBarState
import org.koin.androidx.compose.koinViewModel

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = koinViewModel<CategoryViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

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
    val snackBarState = LocalSnackBarState.current

    LaunchedEffect(state.snackBarState) {
        if (state.snackBarState.isVisible) {
            snackBarState.value = state.snackBarState
            listener.onHideSnakeBar()
        }
    }
    TudeeScaffold(
        systemNavColor = Theme.color.surfaceHigh,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier,
                iconRes = R.drawable.resources_add,
            ) {
                listener.onToggleAddCategorySheet(true)
            }
        },
        topBar = {
            TextAppBar(
                modifier = Modifier
                    .background(color = Theme.color.surfaceHigh)
                    .statusBarsPadding(),
                title = stringResource(R.string.categories)
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Theme.color.surface)
        ) {
            Column {
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
}
