package com.vladimir.cryptocurrency.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vladimir.cryptocurrency.databinding.ActivityCoinPriceListBinding
import com.vladimir.cryptocurrency.domain.CoinInfo
import com.vladimir.cryptocurrency.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CoinViewModel::class.java]
    }

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })

    }
}