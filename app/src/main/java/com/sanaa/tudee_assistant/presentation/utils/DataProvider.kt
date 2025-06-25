package com.sanaa.tudee_assistant.presentation.utils

import android.annotation.SuppressLint
import android.content.Context
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.presentation.model.OnBoardingPageContentItem
import com.sanaa.tudee_assistant.presentation.model.TaskUiPriority
import com.sanaa.tudee_assistant.presentation.model.TaskUiState
import com.sanaa.tudee_assistant.presentation.model.TaskUiStatus
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object DataProvider {

    fun getTasksSample() = listOf(
        TaskUiState(
            id = 1,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 1
        ),
        TaskUiState(
            id = 2,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = "2025-05-12",
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.DONE,
            categoryId = 2
        ),
        TaskUiState(
            id = 3,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
            priority = TaskUiPriority.MEDIUM,
            status = TaskUiStatus.DONE,
            categoryId = 3
        ),
        TaskUiState(
            id = 4,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
            priority = TaskUiPriority.HIGH,
            status = TaskUiStatus.TODO,
            categoryId = 4
        ),
        TaskUiState(
            id = 5,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.IN_PROGRESS,
            categoryId = 5,
        ),
        TaskUiState(
            id = 6,
            title = "Organize Study Desk",
            description = "Review cell structure and functions for tomorrow...",
            dueDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toString(),
            priority = TaskUiPriority.LOW,
            status = TaskUiStatus.TODO,
            categoryId = 6
        ),
    )

    @SuppressLint("DiscouragedApi")
    fun getStringResourceByName(resourceName: String, context: Context): String {
        val allowedResourceName = resourceName.filter { it.isLetterOrDigit() || it == '_' }
        val resourceId = context.resources.getIdentifier(allowedResourceName, "string", context.packageName)
        return if (resourceId != 0) {
            context.getString(resourceId)
        } else {
            resourceName
        }
    }

    fun getOnBoardingPageContent(): List<OnBoardingPageContentItem> {
        return listOf(
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_0,
                description = R.string.onboarding_desc_0,
                imageRes = R.drawable.robot_onboarding_page0
            ),
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_1,
                description = R.string.onboarding_desc_1,
                imageRes = R.drawable.robot_onboarding_page1
            ),
            OnBoardingPageContentItem(
                title = R.string.onboarding_title_2,
                description = R.string.onboarding_desc_2,
                imageRes = R.drawable.robot_onboarding_page2
            ),
        )
    }
}