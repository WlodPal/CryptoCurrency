package com.vladimir.cryptocurrency.di

import com.vladimir.cryptocurrency.data.workers.ChildWorkerFactory
import com.vladimir.cryptocurrency.data.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindsRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory
}