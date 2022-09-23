package com.vladimir.cryptocurrency.presentation

import android.app.Application
import com.vladimir.cryptocurrency.di.DaggerApplicationComponent

class CoinApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}
