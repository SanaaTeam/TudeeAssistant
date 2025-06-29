package com.sanaa.tudee_assistant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.sanaa.tudee_assistant.data.local.dto.CategoryTaskCountDto
import com.sanaa.tudee_assistant.data.local.dto.TaskLocalDto
import com.sanaa.tudee_assistant.domain.entity.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskLocalDto): Long

    @Update
    suspend fun updateTask(task: TaskLocalDto): Int

    @Query("DELETE FROM tasks WHERE task_id = :taskId")
    suspend fun deleteTaskById(taskId: Int): Int

    @Query("DELETE FROM tasks WHERE category_id = :categoryId")
    suspend fun deleteTaskByCategoryId(categoryId: Int): Int

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks(): Int

    @Query("SELECT * FROM tasks WHERE task_id = :taskId")
    fun getTaskById(taskId: Int): Flow<TaskLocalDto?>

    @Query("SELECT * FROM tasks WHERE due_date = :date")
    fun getTasksByDate(date: String): Flow<List<TaskLocalDto>>

    @Query("SELECT * FROM tasks WHERE category_id = :categoryId")
    fun getTasksByCategoryId(categoryId: Int): Flow<List<TaskLocalDto>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskLocalDto>>

    @Query("SELECT * FROM tasks WHERE status = :status")
    fun getTasksByStatus(status: Task.TaskStatus): Flow<List<TaskLocalDto>>

    @Query("SELECT COUNT(*) FROM tasks WHERE category_id = :categoryId")
    fun getTaskCountByCategoryId(categoryId: Int): Flow<Int>

    @Query("SELECT category_id, COUNT(*) as count FROM tasks GROUP BY category_id")
    fun getTaskCountsGroupedByCategoryId(): Flow<List<CategoryTaskCountDto>>
}