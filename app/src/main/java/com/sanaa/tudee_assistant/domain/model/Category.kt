package com.sanaa.tudee_assistant.domain.model

data class Category(
    val id: Int?=null,
    val name: String,
    val imagePath: String,
    val isDefault: Boolean,
)
