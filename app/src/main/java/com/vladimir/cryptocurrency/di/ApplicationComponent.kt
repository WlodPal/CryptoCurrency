package com.vladimir.cryptocurrency.di

import android.app.Application
import com.vladimir.cryptocurrency.presentation.CoinApp
import com.vladimir.cryptocurrency.presentation.CoinDetailFragment
import com.vladimir.cryptocurrency.presentation.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        WorkerModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: CoinPriceListActivity)

    fun inject(fragment: CoinDetailFragment)

    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }

}
