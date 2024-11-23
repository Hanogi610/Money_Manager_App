package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.money_manager_app.data.model.entity.GoalTransaction

@Dao
interface GoalTransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoalTransaction(goalTransaction: GoalTransaction) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGoalTransaction(goalTransaction: GoalTransaction) : Int

    @Delete
    suspend fun deleteGoalTransaction(goalTransaction: GoalTransaction)

    @Query("DELETE FROM goal_transaction WHERE id = :id")
    suspend fun deleteGoalTransactionById(id: Long)
}