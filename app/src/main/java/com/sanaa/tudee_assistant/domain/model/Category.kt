package com.sanaa.tudee_assistant.domain.model

data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val isDefault: Boolean,
)
