package com.sanaa.tudee_assistant.data.services

import com.google.common.truth.Truth.assertThat
import com.sanaa.tudee_assistant.data.local.dao.CategoryDao
import com.sanaa.tudee_assistant.data.local.dto.CategoryLocalDto
import com.sanaa.tudee_assistant.domain.exceptions.DatabaseFailureException
import com.sanaa.tudee_assistant.domain.exceptions.DefaultCategoryException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToAddException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToDeleteException
import com.sanaa.tudee_assistant.domain.exceptions.FailedToUpdateException
import com.sanaa.tudee_assistant.domain.exceptions.NotFoundException
import com.sanaa.tudee_assistant.domain.model.AddCategoryRequest
import com.sanaa.tudee_assistant.domain.model.Category
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoryServiceImplTest {

    private val categoryDao = mockk<CategoryDao>(relaxed = true)
    private lateinit var categoryService: CategoryServiceImpl

    private val fakeCategory = Category(1, "Work", "", isDefault = false)
    private val fakeLocalDto = CategoryLocalDto(1, "Work", "", isDefault = false)

    @BeforeEach
    fun setup() {
        categoryService = CategoryServiceImpl(categoryDao)
    }

    @Test
    fun `getCategoryById should return category when found`() = runTest {
        coEvery { categoryDao.getCategoryById(1) } returns fakeLocalDto

        val result = categoryService.getCategoryById(1)

        assertThat(result.name).isEqualTo("Work")
    }


    @Test
    fun `getCategoryById should throw CategoryNotFoundException when returns null`() = runTest {
        coEvery { categoryDao.getCategoryById(1) } returns null

        assertThrows<NotFoundException> {
            categoryService.getCategoryById(1)
        }
    }

    @Test
    fun `getCategories should emit list of categories`() = runTest {
        every { categoryDao.getAllCategories() } returns flowOf(listOf(fakeLocalDto))

        val result = categoryService.getCategories().toList()

        assertThat(result.flatten()).hasSize(1)
        assertThat(result.flatten().first().name).isEqualTo("Work")
    }

    @Test
    fun `getCategories should throw DatabaseFailureException when flow throws`() = runTest {
        every { categoryDao.getAllCategories() } returns flow { throw RuntimeException("DB error") }

        val result = runCatching { categoryService.getCategories().toList() }

        assertThat(result.exceptionOrNull()).isInstanceOf(DatabaseFailureException::class.java)
    }

    @Test
    fun `addCategory should succeed when insert is successful`() = runTest {
        coEvery { categoryDao.insertCategory(any()) } returns 1L

        categoryService.addCategory(AddCategoryRequest(fakeCategory.name, fakeCategory.imagePath))

        coVerify { categoryDao.insertCategory(match { it.name == fakeCategory.name }) }
    }

    @Test
    fun `addCategory should throw FailedToAddCategoryException when insert fails`() = runTest {
        coEvery { categoryDao.insertCategory(any()) } returns -1L

        val result = runCatching {
            categoryService.addCategory(
                AddCategoryRequest(
                    fakeCategory.name,
                    fakeCategory.imagePath
                )
            )
        }

        assertThat(result.exceptionOrNull()).isInstanceOf(FailedToAddException::class.java)
    }

    @Test
    fun `updateCategory should succeed when update is successful`() = runTest {
        val category = Category(1, "Work", "", isDefault = false)
        coEvery { categoryDao.updateCategory(any()) } returns 1

        categoryService.updateCategory(category)

        coVerify(exactly = 1) {
            categoryDao.updateCategory(match { it.name == "Work" && it.categoryId == 1 })
        }
    }

    @Test
    fun `updateCategory should throw FailedToUpdateCategoryException when update fails`() =
        runTest {
            coEvery { categoryDao.updateCategory(any()) } returns 0

            val result = runCatching { categoryService.updateCategory(fakeCategory) }

            assertThat(result.exceptionOrNull()).isInstanceOf(FailedToUpdateException::class.java)
        }

    @Test
    fun `deleteCategoryById should succeed when deletion is successful`() = runTest {
        val category = CategoryLocalDto(1, "Work", "", isDefault = false)
        coEvery { categoryDao.getCategoryById(1) } returns category
        coEvery { categoryDao.deleteCategoryById(1) } returns 1

        categoryService.deleteCategoryById(1)

        coVerify(exactly = 1) { categoryDao.getCategoryById(1) }
        coVerify(exactly = 1) { categoryDao.deleteCategoryById(1) }
    }


    @Test
    fun `deleteCategoryById should throw CategoryNotFoundException when category not found`() =
        runTest {
            coEvery { categoryDao.getCategoryById(1) } returns null

            val result = runCatching { categoryService.deleteCategoryById(1) }

            assertThat(result.exceptionOrNull()).isInstanceOf(NotFoundException::class.java)
        }

    @Test
    fun `deleteCategoryById should throw DefaultCategoryException when isDefault is true`() =
        runTest {
            coEvery { categoryDao.getCategoryById(1) } returns fakeLocalDto.copy(isDefault = true)

            val result = runCatching { categoryService.deleteCategoryById(1) }

            assertThat(result.exceptionOrNull()).isInstanceOf(DefaultCategoryException::class.java)
        }

    @Test
    fun `deleteCategoryById should throw FailedToDeleteCategoryException when deletion fails`() =
        runTest {
            coEvery { categoryDao.getCategoryById(1) } returns fakeLocalDto
            coEvery { categoryDao.deleteCategoryById(1) } returns 0

            val result = runCatching { categoryService.deleteCategoryById(1) }

            assertThat(result.exceptionOrNull()).isInstanceOf(FailedToDeleteException::class.java)
        }
}
