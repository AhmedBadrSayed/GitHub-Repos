package com.mondiamedia.ahmedbadr.githubreopos.di

import com.mondiamedia.ahmedbadr.githubreopos.api.RepositoriesService

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRepositoriesService(): RepositoriesService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RepositoriesService::class.java)
    }

    companion object {
        private const val BASE_URL = "https://github-trending-api.now.sh"
    }
}
