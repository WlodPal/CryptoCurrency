package com.vladimir.cryptocurrency.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vladimir.cryptocurrency.data.repository.CoinRepositoryImpl
import com.vladimir.cryptocurrency.domain.CoinInfo
import com.vladimir.cryptocurrency.domain.GetCoinInfoListUseCase
import com.vladimir.cryptocurrency.domain.GetCoinInfoUseCase
import com.vladimir.cryptocurrency.domain.LoadDataUseCase
import kotlinx.coroutines.launch

class CoinViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CoinRepositoryImpl(application)

    private val getCoinInfoListUseCase = GetCoinInfoListUseCase(repository)
    private val getCoinInfoUseCase = GetCoinInfoUseCase(repository)
    private val loadDataUseCase = LoadDataUseCase(repository)

    val coinInfoList = getCoinInfoListUseCase()

    fun getDetailInfo(fSym: String) = getCoinInfoUseCase(fSym)

    private val _coinInfo = MutableLiveData<CoinInfo>()
    val coinInfo: LiveData<CoinInfo>
        get() = _coinInfo

    init {
        loadDataUseCase()
    }
}