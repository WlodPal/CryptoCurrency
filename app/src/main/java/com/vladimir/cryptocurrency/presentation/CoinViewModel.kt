package com.vladimir.cryptocurrency.presentation

import androidx.lifecycle.ViewModel
import com.vladimir.cryptocurrency.domain.GetCoinInfoListUseCase
import com.vladimir.cryptocurrency.domain.GetCoinInfoUseCase
import com.vladimir.cryptocurrency.domain.LoadDataUseCase
import javax.inject.Inject

class CoinViewModel @Inject constructor (
    private val getCoinInfoUseCase: GetCoinInfoUseCase,
    private val getCoinInfoListUseCase: GetCoinInfoListUseCase,
    private val loadDataUseCase: LoadDataUseCase
) : ViewModel() {

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    init {
        loadDataUseCase()
    }
}