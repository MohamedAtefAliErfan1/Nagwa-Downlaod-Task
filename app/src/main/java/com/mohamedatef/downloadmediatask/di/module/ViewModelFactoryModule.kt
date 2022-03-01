package com.mohamedatef.downloadmediatask.di.module

import androidx.lifecycle.ViewModelProvider
import com.mohamedatef.downloadmediatask.di.factories.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}