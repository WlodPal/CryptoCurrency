package com.vladimir.cryptocurrency.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vladimir.cryptocurrency.R
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
                if (isOnePaneMode()) {
                    lunchDetailActivity(coinInfo.fromSymbol)
                } else {
                    lunchDetailFragment(coinInfo.fromSymbol)
                }

            }
        }
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun lunchDetailActivity(fromSymbols: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbols
        )
        startActivity(intent)
    }


    private fun lunchDetailFragment(fromSymbols: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbols))
            .addToBackStack(null)
            .commit()
    }
}