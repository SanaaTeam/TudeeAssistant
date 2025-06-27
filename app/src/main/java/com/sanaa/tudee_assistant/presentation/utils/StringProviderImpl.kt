package com.sanaa.tudee_assistant.presentation.utils

import android.content.Context
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.StringProvider

class StringProviderImpl(
    private val context: Context,
): StringProvider {
    override val unknownError: String
        get() = context.getString(R.string.unknown_error)
    override val taskAddedSuccess: String
        get() = context.getString(R.string.task_added_success)
    override val taskUpdateSuccess: String
        get() = context.getString(R.string.task_update_success)
    override val taskDeleteSuccess: String
        get() = context.getString(R.string.task_delete_success)
    override val taskStatusUpdateSuccess: String
        get() = context.getString(R.string.task_status_update_success)
    override val taskStatusUpdateError: String
        get() = context.getString(R.string.task_status_update_error)
    override val categoryAddedSuccessfully: String
        get() = context.getString(R.string.category_added_successfully)
    override val categoryUpdateSuccessfully: String
        get() = context.getString(R.string.category_update_successfully)
    override val deletedCategorySuccessfully: String
        get() = context.getString(R.string.deleted_category_successfully)
    override val deletingCategoryError: String
        get() = context.getString(R.string.deleting_category_error)
    override val markAsInProgress: String
        get() = context.getString(R.string.mark_as_in_progress)
    override val markAsDone: String
        get() = context.getString(R.string.mark_as_done)
}