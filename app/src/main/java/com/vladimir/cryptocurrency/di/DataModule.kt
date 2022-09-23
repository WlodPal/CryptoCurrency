package com.vladimir.cryptocurrency.di

import android.app.Application
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.database.CoinInfoDao
import com.vladimir.cryptocurrency.data.repository.CoinRepositoryImpl
import com.vladimir.cryptocurrency.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository


    companion object {

        @Provides
        fun provideCoinInfoDao(
            application: Application
        ) : CoinInfoDao {
            return AppDataBase.getInstance(application).coinPriceInfoDao()
        }

    }
}