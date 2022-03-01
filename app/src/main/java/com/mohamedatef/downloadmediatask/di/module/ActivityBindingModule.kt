package com.mohamedatef.downloadmediatask.di.module

import dagger.android.ContributesAndroidInjector
import com.mohamedatef.downloadmediatask.ui.MainActivity
import dagger.Module

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity?
}