package com.sanaa.tudee_assistant.domain.model

import java.util.UUID

data class UserAddedCategory(
    override val id: String = UUID.randomUUID().toString(),
    override val title: String,
    override val iconResource: Int
) : Category