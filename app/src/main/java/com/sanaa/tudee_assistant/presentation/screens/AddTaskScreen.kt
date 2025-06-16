package com.sanaa.tudee_assistant.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.design_system.component.BaseBottomSheet
import com.sanaa.tudee_assistant.presentation.design_system.component.CategoryItem
import com.sanaa.tudee_assistant.presentation.design_system.component.CheckMarkContainer
import com.sanaa.tudee_assistant.presentation.design_system.component.TudeeTextField
import com.sanaa.tudee_assistant.presentation.design_system.theme.Theme
import com.sanaa.tudee_assistant.presentation.design_system.component.PriorityTag
import com.sanaa.tudee_assistant.presentation.design_system.component.button.PrimaryButton
import com.sanaa.tudee_assistant.presentation.design_system.component.button.SecondaryButton
import com.sanaa.tudee_assistant.presentation.model.CategoryState
import com.sanaa.tudee_assistant.presentation.model.TaskPriority


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen() {
    val categories = listOf(
        CategoryState(
            title = "Education",
            categoryPainter = painterResource(R.drawable.education_cat),
            tint = Theme.color.purpleAccent
        ),
        CategoryState(
            title = "Shopping",
            categoryPainter = painterResource(R.drawable.shopping_cat),
            tint = Theme.color.secondary
        ),
        CategoryState(
            title = "Medical",
            categoryPainter = painterResource(R.drawable.medical_cat),
            tint = Theme.color.primary
        ),
        CategoryState(
            title = "Gym",
            categoryPainter = painterResource(R.drawable.gym_cat),
            tint = Theme.color.primary
        ),
        CategoryState(
            title = "Entertainment",
            categoryPainter = painterResource(R.drawable.entertainment_cat),
            tint = Theme.color.yellowAccent
        ),
        CategoryState(
            title = "Cooking",
            categoryPainter = painterResource(R.drawable.cooking_cat),
            tint = Theme.color.pinkAccent
        ),
        CategoryState(
            title = "Family & friend",
            categoryPainter = painterResource(R.drawable.family_friend_cat),
            tint = Theme.color.secondary
        ),
        CategoryState(
            title = "Traveling",
            categoryPainter = painterResource(R.drawable.traveling_cat),
            tint = Theme.color.yellowAccent
        ),
        CategoryState(
            title = "Agriculture",
            categoryPainter = painterResource(R.drawable.agriculture_cat),
            tint = Theme.color.greenAccent
        ),
        CategoryState(
            title = "Coding",
            categoryPainter = painterResource(R.drawable.coding_cat),
            tint = Theme.color.purpleAccent
        ),
        CategoryState(
            title = "Adoration",
            categoryPainter = painterResource(R.drawable.adoration_cat),
            tint = Theme.color.primary
        ),
        CategoryState(
            title = "Fixing bugs",
            categoryPainter = painterResource(R.drawable.fixing_bugs_cat),
            tint = Theme.color.secondary
        ),
        CategoryState(
            title = "Cleaning",
            categoryPainter = painterResource(R.drawable.cleaning_cat),
            tint = Theme.color.greenAccent
        ),
        CategoryState(
            title = "Work",
            categoryPainter = painterResource(R.drawable.work_cat),
            tint = Theme.color.secondary
        ),
        CategoryState(
            title = "Budgeting",
            categoryPainter = painterResource(R.drawable.budgeting_cat),
            tint = Theme.color.purpleAccent
        ),
        CategoryState(
            title = "Self-care",
            categoryPainter = painterResource(R.drawable.self_care_cat),
            tint = Theme.color.yellowAccent
        ),
        CategoryState(
            title = "Event",
            categoryPainter = painterResource(R.drawable.event_cat),
            tint = Theme.color.pinkAccent
        )
    )
    var showBottomSheet by remember { mutableStateOf(true) }
    var selectedCategory by remember { mutableStateOf<CategoryState?>(null) }

    if (showBottomSheet) {
        BaseBottomSheet(
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(.81f)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f)
                            .padding(Theme.dimension.medium)
                    ) {
                        Text(
                            text = stringResource(R.string.add_new_task),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )

                        TudeeTextField(
                            placeholder = stringResource(R.string.task_title),
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.dimension.medium),
                            icon = painterResource(R.drawable.task),
                        )
                        TudeeTextField(
                            placeholder = stringResource(R.string.description),
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(168.dp)
                                .padding(bottom = Theme.dimension.medium),
                        )
                        TudeeTextField(
                            placeholder = stringResource(R.string.date_placeholder),
                            value = "",
                            onValueChange = {},
                            icon = painterResource(R.drawable.calender),
                            modifier = Modifier
                                .padding(bottom = Theme.dimension.medium)
                        )
                        Text(
                            text = stringResource(R.string.priority),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )
                        Row(
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            horizontalArrangement = Arrangement.spacedBy(Theme.dimension.small)
                        ) {
                            PriorityTag(priority = TaskPriority.HIGH)
                            PriorityTag(priority = TaskPriority.MEDIUM)
                            PriorityTag(priority = TaskPriority.LOW)
                        }

                        Text(
                            text = stringResource(R.string.category),
                            modifier = Modifier.padding(bottom = Theme.dimension.medium),
                            fontSize = Theme.textStyle.title.large.fontSize,
                            color = Theme.color.title
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min=100.dp,max = 900.dp),
                            userScrollEnabled = false,
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(categories.size) { index ->
                                val category = categories[index]
                                CategoryItem(
                                    category = category,
                                    modifier = Modifier,
                                    onClick = { selectedCategory = category },
                                    topContent = {
                                        if (category == selectedCategory) {
                                            CheckMarkContainer()
                                        }
                                    }
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Theme.color.surfaceHigh)
                            .padding(vertical = Theme.dimension.regular, horizontal = Theme.dimension.medium)
                    ) {
                        PrimaryButton(
                            label = stringResource(R.string.add),
                            modifier = Modifier.fillMaxWidth().padding(bottom = Theme.dimension.regular),
                            onClick = {  }
                        )
                        SecondaryButton(
                            lable = stringResource(R.string.cancel),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { showBottomSheet = false },

                        )
                    }
                }
            },
            onDismiss = { showBottomSheet = false }
        )
    }
}