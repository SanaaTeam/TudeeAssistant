package com.sanaa.tudee_assistant.domain.service

interface StringProvider {
    val unknownError: String


    val taskAddedSuccess: String
    val taskUpdateSuccess: String
    val taskDeleteSuccess: String
    val taskStatusUpdateSuccess: String

    val taskAddedError: String
    val taskUpdateError: String
    val taskDeleteError: String
    val taskStatusUpdateError: String

    val categoryAddedSuccessfully: String
    val categoryUpdateSuccessfully: String
    val deletedCategorySuccessfully: String

    val categoryAddedError: String
    val updateCategoryError: String
    val deletingCategoryError: String


    val markAsInProgress: String
    val markAsDone: String




}