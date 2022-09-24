package com.vladimir.cryptocurrency.data.workers

import android.content.Context
import androidx.work.*
import com.vladimir.cryptocurrency.R
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.database.CoinInfoDao
import com.vladimir.cryptocurrency.data.mapper.CoinMapper
import com.vladimir.cryptocurrency.data.network.ApiFactory
import com.vladimir.cryptocurrency.data.network.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker (
    context: Context,
    workerParameters: WorkerParameters,
    private val coinInfoDao: CoinInfoDao,
    private val apiService: ApiService,
    private val mapper: CoinMapper
): CoroutineWorker(context, workerParameters) {


    override suspend fun doWork(): Result {
        while (true) {
            try {
                val coinInfo = apiService.getTopCoinsInfo(limit = 50)
                val fSyms = mapper.mapNamesListToString(coinInfo)
                val jasonContainer = apiService.getFullPRiceList(fSyms = fSyms)
                val coinInfoDtoList = mapper.mapJasonContainerToListCoinInfo(jasonContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {
            }
            delay(10000)
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<RefreshDataWorker>().build()
        }
    }

    class Factory @Inject constructor(
        private val coinInfoDao: CoinInfoDao,
        private val apiService: ApiService,
        private val mapper: CoinMapper
    ): ChildWorkerFactory {
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                coinInfoDao,
                apiService,
                mapper
            )
        }

    }

}