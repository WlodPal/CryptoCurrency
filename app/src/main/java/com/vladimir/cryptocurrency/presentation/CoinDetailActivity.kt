package com.vladimir.cryptocurrency.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.vladimir.cryptocurrency.databinding.ActivityCoinDetailBinding
import com.vladimir.cryptocurrency.presentation.adapters.CoinInfoAdapter

class CoinDetailActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL) ?: EMPTY_SYMBOL
        viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            with(binding) {
                tvFullPrice.text = it.price
                tvMinForTheDay.text = it.lowDay
                tvMaxForTheDay.text = it.highDay
                tvLastDeal.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbols.text = it.fromSymbol
                tvToSymbols.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        })
    }


    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val EMPTY_SYMBOL = ""

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}