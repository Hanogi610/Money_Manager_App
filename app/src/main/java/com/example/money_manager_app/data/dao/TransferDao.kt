package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.money_manager_app.data.model.entity.Transfer
import kotlinx.coroutines.flow.Flow

@Dao
interface TransferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransfer(transfer: Transfer): Long

    @Query("SELECT * FROM transfer WHERE transfer_date BETWEEN :startDay AND :endDay AND account_id = :accountId")
    fun getTransferFromDayStartAndDayEnd(startDay : Long, endDay : Long, accountId : Long): Flow<List<Transfer>>

    @Query("SELECT * FROM transfer WHERE transfer_date = :date")
    fun getAllTransfer(date : Long): Flow<List<Transfer>>
}
