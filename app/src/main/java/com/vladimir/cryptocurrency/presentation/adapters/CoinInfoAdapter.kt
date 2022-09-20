package com.vladimir.cryptocurrency.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import com.vladimir.cryptocurrency.R
import com.vladimir.cryptocurrency.databinding.ItemCoinInfoBinding
import com.vladimir.cryptocurrency.domain.CoinInfo

class CoinInfoAdapter(private val context: Context):
    ListAdapter<CoinInfo, CoinInfoViewHolder>(ShopListDiffCallBack) {


    var onCoinClickListener: OnCoinClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder.binding) {
            with(coin) {
                val symbolsTemplate = root.context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate =
                    root.context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvUpdateTime.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivLogoMain)
                root.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinInfo: CoinInfo)
    }
}
