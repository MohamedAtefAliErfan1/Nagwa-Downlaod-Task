package com.mohamedatef.downloadmediatask.di.component

import javax.inject.Singleton
import com.mohamedatef.downloadmediatask.di.module.ContextModule
import com.mohamedatef.downloadmediatask.di.module.ApplicationModule
import dagger.android.support.AndroidSupportInjectionModule
import com.mohamedatef.downloadmediatask.di.module.ActivityBindingModule
import dagger.android.AndroidInjector
import com.mohamedatef.downloadmediatask.base.BaseApplication
import dagger.BindsInstance
import android.app.Application
import com.mohamedatef.downloadmediatask.di.component.ApplicationComponent
import com.mohamedatef.downloadmediatask.di.module.ViewModelFactoryModule
import dagger.Component
import dagger.android.support.DaggerApplication

@Singleton
@Component(modules = [ContextModule::class, ApplicationModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class,ViewModelFactoryModule::class])
interface ApplicationComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}