package com.sanaa.tudee_assistant.data.services

import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.mapper.toDomain
import com.sanaa.tudee_assistant.data.local.mapper.toLocalDto
import com.sanaa.tudee_assistant.domain.exceptions.DatabaseFailureException
import com.sanaa.tudee_assistant.domain.exceptions.DefaultCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.NotFoundException
import com.sanaa.tudee_assistant.domain.model.Category
import com.sanaa.tudee_assistant.domain.model.NewCategory
import com.sanaa.tudee_assistant.domain.service.CategoryService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class CategoryServiceImpl(
    private val categoryDao: CategoryDao
) : CategoryService {
    override suspend fun getCategoryById(categoryId: Int): Category {
        return categoryDao.getCategoryById(categoryId)?.toDomain()
            ?: throw NotFoundException()
    }

    override fun getCategories(): Flow<List<Category>> =
        categoryDao.getAllCategories()
            .map { list ->
                list.map { it.toDomain() }
            }.catch {
                throw DatabaseFailureException(
                    message = "Failed to load categories from database duo to database error details :${it.message} ",
                    cause = it
                )
            }

    override suspend fun addCategory(newCategory: NewCategory) {
        if (categoryDao.insertCategory(newCategory.toLocalDto()) == -1L) {
            throw FailedToAddCategoryException()
        }
    }

    override suspend fun updateCategory(category: Category) {
        if (categoryDao.updateCategory(category.toLocalDto()) <= 0) {
            throw FailedToUpdateCategoryException()
        }
    }

    override suspend fun deleteCategoryById(categoryId: Int) {
        val category = categoryDao.getCategoryById(categoryId)
            ?: throw NotFoundException()
        if (category.isDefault) {
            throw DefaultCategoryException()
        }
        if (categoryDao.deleteCategoryById(categoryId) <= 0) {
            throw FailedToDeleteCategoryException()
        }
    }

    override suspend fun deleteAllCategories() {
        if (categoryDao.deleteAllCategory() <= 0) {
            throw FailedToDeleteCategoryException()
        }
    }
}