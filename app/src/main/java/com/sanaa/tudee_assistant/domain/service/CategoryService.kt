package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    suspend fun getCategoryById(categoryId: Int): Category?
    fun getCategories(): Flow<List<Category>>
    suspend fun addCategory(category: Category): Boolean
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategoryById(categoryId: Int): Boolean
    suspend fun deleteAllCategories()
}