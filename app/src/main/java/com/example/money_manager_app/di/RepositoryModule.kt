package com.example.moneymanager.di

import com.example.money_manager_app.data.repository.AccountRepository
import com.example.money_manager_app.data.repository.AccountRepositoryImpl
import com.example.money_manager_app.data.repository.DebtRepository
import com.example.money_manager_app.data.repository.DebtRepositoryImpl
import com.example.money_manager_app.data.repository.DebtTransactionRepository
import com.example.money_manager_app.data.repository.DebtTransactionRepositoryImpl
import com.example.money_manager_app.data.repository.TransferRepository
import com.example.money_manager_app.data.repository.TransferRepositoryImpl
import com.example.money_manager_app.data.repository.WalletRepository
import com.example.money_manager_app.data.repository.WalletRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindTransferRepository(transferRepositoryImpl: TransferRepositoryImpl): TransferRepository

    @Binds
    @Singleton
    abstract fun bindWalletRepository(walletRepositoryImpl: WalletRepositoryImpl): WalletRepository

    @Binds
    @Singleton
    abstract fun bindDebtRepository(debtRepositoryImpl: DebtRepositoryImpl): DebtRepository

    @Binds
    @Singleton
    abstract fun bindDebtTransactionRepository(debtTransactionRepositoryImpl: DebtTransactionRepositoryImpl): DebtTransactionRepository
}