package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.entity.Category
import com.sanaa.tudee_assistant.domain.entity.CategoryCreationRequest
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(categoryId: Int): Category
    suspend fun addCategory(categoryCreationRequest: CategoryCreationRequest)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategoryById(categoryId: Int)
}