package com.example.money_manager_app.data.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.money_manager_app.data.dao.CategoryDao
import com.example.money_manager_app.data.model.entity.Category
import com.example.money_manager_app.data.model.entity.CategoryWithTransfer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CategoryRepository {
    suspend fun insertCategory(category: Category)

    suspend fun editCategory(category: Category)

    suspend fun deleteCategory(category: Category)

    suspend fun deleteCategory(categoryId: Long)

    fun getCategoriesWithTransferByAccountId(accountId: Long): Flow<List<CategoryWithTransfer>>

    fun getCategoriesByAccountId(accountId: Long): Flow<List<Category>>
}

class CategoryRepositoryImpl @Inject constructor(private val categoryDao: CategoryDao) : CategoryRepository {
    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    override suspend fun editCategory(category: Category) {
        categoryDao.editCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    override suspend fun deleteCategory(categoryId: Long) {
        categoryDao.deleteCategory(categoryId)
    }

    override fun getCategoriesWithTransferByAccountId(accountId: Long): Flow<List<CategoryWithTransfer>> {
        return categoryDao.getCategoriesWithTransferByAccountId(accountId)
    }

    override fun getCategoriesByAccountId(accountId: Long): Flow<List<Category>> {
        return categoryDao.getCategoriesByAccountId(accountId)
    }
}