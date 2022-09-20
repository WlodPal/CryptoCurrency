package com.vladimir.cryptocurrency.data.mapper

import com.google.gson.Gson
import com.vladimir.cryptocurrency.data.database.CoinInfoDbModel
import com.vladimir.cryptocurrency.data.network.model.CoinInfoDto
import com.vladimir.cryptocurrency.data.network.model.CoinInfoJasonContainerDto
import com.vladimir.cryptocurrency.data.network.model.CoinNamesListDto
import com.vladimir.cryptocurrency.domain.CoinInfo
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class CoinMapper {


    fun mapDtoToDbModel(coinInfoDto: CoinInfoDto)= CoinInfoDbModel (
        fromSymbol = coinInfoDto.fromSymbol,
        toSymbol = coinInfoDto.toSymbol,
        price = coinInfoDto.price,
        lastUpdate = coinInfoDto.lastUpdate,
        highDay = coinInfoDto.highDay,
        lowDay = coinInfoDto.lowDay,
        lastMarket = coinInfoDto.lastMarket,
        imageUrl = BASE_IMAGE_URL +  coinInfoDto.imageUrl
            )

    fun mapJasonContainerToListCoinInfo(
        coinInfoJasonContainer: CoinInfoJasonContainerDto
    ): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jasonObject = coinInfoJasonContainer.json ?: return result
        val coinKeySet = jasonObject.keySet()
        for (coinKey in coinKeySet) {
            val currencyJson = jasonObject.getAsJsonObject(coinKey)
            val currencyKeySet = currencyJson.keySet()
            for (currencyKey in currencyKeySet) {
                val priceInfo = Gson().fromJson(
                    currencyJson.getAsJsonObject(currencyKey),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return  namesListDto.names?.map {
            it.coinName?.name
        }?.joinToString(",") ?: ""
    }


    fun mapDbModelToEntity(coinInfoDbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = coinInfoDbModel.fromSymbol,
        toSymbol = coinInfoDbModel.toSymbol,
        price = coinInfoDbModel.price,
        lastUpdate = convertTimestampToTime( coinInfoDbModel.lastUpdate),
        highDay = coinInfoDbModel.highDay,
        lowDay = coinInfoDbModel.lowDay,
        lastMarket = coinInfoDbModel.lastMarket,
        imageUrl = coinInfoDbModel.imageUrl
    )



    private fun convertTimestampToTime(timestamp: Long?) : String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}