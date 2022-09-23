package com.vladimir.cryptocurrency.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.vladimir.cryptocurrency.databinding.ItemCoinInfoBinding
import javax.inject.Inject

class CoinInfoViewHolder @Inject constructor(
    val binding: ItemCoinInfoBinding
) : RecyclerView.ViewHolder(binding.root)