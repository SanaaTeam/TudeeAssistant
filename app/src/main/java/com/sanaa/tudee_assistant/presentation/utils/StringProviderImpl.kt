package com.sanaa.tudee_assistant.presentation.utils

import android.content.Context
import com.sanaa.tudee_assistant.R
import com.sanaa.tudee_assistant.domain.service.StringProvider

class StringProviderImpl(
    private val context: Context
): StringProvider {
    override val unknown_error: String
        get() = context.getString(R.string.unknown_error)
    override val task_added_success: String
        get() = context.getString(R.string.task_added_success)
    override val task_update_success: String
        get() = context.getString(R.string.task_update_success)
    override val task_delete_success: String
        get() = context.getString(R.string.task_delete_success)
    override val task_status_update_success: String
        get() = context.getString(R.string.task_status_update_success)
    override val task_added_error: String
        get() = context.getString(R.string.task_added_error)
    override val task_update_error: String
        get() = context.getString(R.string.task_update_error)
    override val task_delete_error: String
        get() = context.getString(R.string.task_delete_error)
    override val task_status_update_error: String
        get() = context.getString(R.string.task_status_update_error)
    override val category_added_successfully: String
        get() = context.getString(R.string.category_added_successfully)
    override val category_update_successfully: String
        get() = context.getString(R.string.category_update_successfully)
    override val deleted_category_successfully: String
        get() = context.getString(R.string.deleted_category_successfully)
    override val category_added_error: String
        get() = context.getString(R.string.category_added_error)
    override val update_category_error: String
        get() = context.getString(R.string.update_category_error)
    override val deleting_category_error: String
        get() = context.getString(R.string.deleting_category_error)

}