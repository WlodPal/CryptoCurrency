package com.vladimir.cryptocurrency.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.vladimir.cryptocurrency.data.database.AppDataBase
import com.vladimir.cryptocurrency.data.mapper.CoinMapper
import com.vladimir.cryptocurrency.data.network.ApiFactory
import com.vladimir.cryptocurrency.domain.CoinRepository
import com.vladimir.cryptocurrency.domain.CoinInfo
import kotlinx.coroutines.delay

class CoinRepositoryImpl(application: Application): CoinRepository {

    private val coinInfoDao = AppDataBase.getInstance(application).coinPriceInfoDao()
    private val apiService = ApiFactory.apiService

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

    override suspend fun loadData() {
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


}