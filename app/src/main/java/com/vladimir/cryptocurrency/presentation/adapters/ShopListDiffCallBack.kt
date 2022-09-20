package com.vladimir.cryptocurrency.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.vladimir.cryptocurrency.domain.CoinInfo

object ShopListDiffCallBack: DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem == newItem
    }
}