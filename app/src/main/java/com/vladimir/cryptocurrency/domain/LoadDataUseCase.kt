package com.vladimir.cryptocurrency.domain

class LoadDataUseCase(private val coinRepository: CoinRepository) {

    operator fun invoke() = coinRepository.loadData()
}