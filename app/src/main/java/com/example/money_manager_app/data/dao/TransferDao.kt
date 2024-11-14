package com.example.moneymanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneymanager.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransfer(transfer: Transfer): Long

    @Query("SELECT * FROM transfer WHERE transfer_date = :date")
    fun getAllTransfer(date : Long): Flow<List<Transfer>>
}
