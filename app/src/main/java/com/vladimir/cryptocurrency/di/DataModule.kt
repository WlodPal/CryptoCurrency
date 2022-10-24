package com.vladimir.cryptocurrency.di

import android.app.Application
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.database.CoinInfoDao
import com.vladimir.cryptocurrency.data.network.ApiFactory
import com.vladimir.cryptocurrency.data.network.ApiService
import com.vladimir.cryptocurrency.data.repository.CoinRepositoryImpl
import com.vladimir.cryptocurrency.domain.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository


    companion object {

        @Provides
        @ApplicationScope
        fun provideCoinInfoDao(
            application: Application
        ) : CoinInfoDao {
            return AppDataBase.getInstance(application).coinPriceInfoDao()
        }


        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

    }
}