package com.sanaa.tudee_assistant.domain.service

interface StringProvider {
    val unknownError: String


    val taskAddedSuccess: String
    val taskUpdateSuccess: String
    val taskDeleteSuccess: String
    val taskStatusUpdateSuccess: String

    val taskStatusUpdateError: String

    val categoryAddedSuccessfully: String
    val categoryUpdateSuccessfully: String
    val deletedCategorySuccessfully: String

    val deletingCategoryError: String

    val markAsInProgress: String
    val markAsDone: String

    val goodStatusMessageTitle: String
    val okayStatusMessageTitle: String
    val poorStatusMessageTitle: String
    val badStatusMessageTitle: String

    val goodStatusMessage: String
    val okayStatusMessage: String
    val poorStatusMessage: String
    val badStatusMessage: String

    fun formattedOkayStatusMessage(done: Int, total: Int): String

}