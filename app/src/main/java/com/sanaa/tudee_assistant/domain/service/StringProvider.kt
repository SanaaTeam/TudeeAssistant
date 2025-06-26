package com.sanaa.tudee_assistant.domain.service

interface StringProvider {
    val unknown_error: String


    val task_added_success: String
    val task_update_success: String
    val task_delete_success: String
    val task_status_update_success: String


    val task_added_error: String
    val task_update_error: String
    val task_delete_error: String
    val task_status_update_error: String



    val category_added_successfully: String
    val category_update_successfully: String
    val deleted_category_successfully: String

    val category_added_error: String
    val update_category_error: String
    val deleting_category_error: String

}