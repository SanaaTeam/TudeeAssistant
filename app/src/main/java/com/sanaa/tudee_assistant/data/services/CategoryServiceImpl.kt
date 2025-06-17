package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryServiceImpl(
    private val categoryDao: CategoryDao
) : CategoryService {
    override suspend fun getCategoryById(categoryId: Int): Category? {
        return categoryDao.getCategoryById(categoryId)?.toDomain()
    }

    override fun getCategories(): Flow<List<Category>> = categoryDao.getAllCategories()
        .map { list -> list.map { it.toDomain() } }

    override suspend fun addCategory(category: Category): Boolean {
        return categoryDao.insertCategory(category.toLocalDto()) != -1L
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return categoryDao.updateCategory(category.toLocalDto()) > 0
    }

    override suspend fun deleteCategoryById(categoryId: Int): Boolean {
        return categoryDao.deleteCategoryById(categoryId) > 0
    }

    override suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategory()
    }
}