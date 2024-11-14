package com.example.money_manager_app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.money_manager_app.data.model.entity.Transfer

@Dao
interface TransferDao {

    @Insert
    suspend fun insertTransfer(transfer: Transfer) : Long
}
