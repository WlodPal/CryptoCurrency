package com.vladimir.cryptocurrency.data.network.model

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJasonContainerDto (
    @SerializedName("RAW")
    @Expose
    val json: JsonObject? = null

)