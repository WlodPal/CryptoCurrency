package com.vladimir.cryptocurrency.data.workers

import android.content.Context
import androidx.work.*
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.mapper.CoinMapper
import com.vladimir.cryptocurrency.data.network.ApiFactory
import kotlinx.coroutines.delay

class RefreshDataWorker(
    context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {

    private val coinInfoDao = AppDataBase.getInstance(context).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

    private val mapper = CoinMapper()


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

}