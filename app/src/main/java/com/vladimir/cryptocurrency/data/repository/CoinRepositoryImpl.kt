package com.vladimir.cryptocurrency.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.mapper.CoinMapper
import com.vladimir.cryptocurrency.data.workers.RefreshDataWorker
import com.vladimir.cryptocurrency.domain.CoinInfo
import com.vladimir.cryptocurrency.domain.CoinRepository

class CoinRepositoryImpl(private val application: Application): CoinRepository {

    private val coinInfoDao = AppDataBase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()


    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return Transformations.map(coinInfoDao.getPriceList()) {
            it.map {
                mapper.mapDbModelToEntity(it)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> = Transformations.map(
        coinInfoDao.getPriceInfoAboutCoin(fromSymbol)
    ){
        mapper.mapDbModelToEntity(it)
    }

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}