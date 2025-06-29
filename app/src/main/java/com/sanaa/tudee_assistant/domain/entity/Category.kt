package com.sanaa.tudee_assistant.domain.entity

data class Category(
    val id: Int,
    val name: String,
    val imagePath: String,
    val isDefault: Boolean,
)
