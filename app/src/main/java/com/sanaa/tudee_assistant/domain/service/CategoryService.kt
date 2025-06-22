package com.sanaa.tudee_assistant.domain.service

import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.NewCategory
import kotlinx.coroutines.flow.Flow

interface CategoryService {
    fun getCategories(): Flow<List<Category>>
    suspend fun getCategoryById(categoryId: Int): Category
    suspend fun addCategory(newCategory: NewCategory)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategoryById(categoryId: Int)
    suspend fun deleteAllCategories()
}