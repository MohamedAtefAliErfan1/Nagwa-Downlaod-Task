package com.mohamedatef.downloadmediatask.di.module

import dagger.Binds
import dagger.multibindings.IntoMap

import com.mohamedatef.downloadmediatask.ui.DownloadViewModel
import androidx.lifecycle.ViewModel
import com.mohamedatef.downloadmediatask.di.key.ViewModelKey
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(DownloadViewModel::class)
    abstract fun bindDownloadViewModel(downloadViewModel: DownloadViewModel?): ViewModel?
}