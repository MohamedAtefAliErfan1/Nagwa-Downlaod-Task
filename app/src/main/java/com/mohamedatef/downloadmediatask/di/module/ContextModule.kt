package com.mohamedatef.downloadmediatask.di.module

import dagger.Binds
import android.app.Application
import android.content.Context
import dagger.Module

@Module
abstract class ContextModule {
    @Binds
    abstract fun provideContext(application: Application?): Context?
}