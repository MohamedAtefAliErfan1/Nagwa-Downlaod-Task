package com.mohamedatef.downloadmediatask.di.module

import com.mohamedatef.downloadmediatask.di.module.ViewModelModule
import javax.inject.Singleton
import retrofit2.Retrofit
import com.mohamedatef.downloadmediatask.di.module.ApplicationModule
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.mohamed_atef.nagwatask.data.rest.DownloadService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://nagwa.free.beeceptor.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit): DownloadService {
        return retrofit.create(DownloadService::class.java)
    }
}