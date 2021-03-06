package com.mondiamedia.ahmedbadr.githubreopos.di.modules;

import com.mondiamedia.ahmedbadr.githubreopos.api.RepositoriesService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private static final String BASE_URL = "https://github-trending-api.now.sh";

    @Provides
    public RepositoriesService provideRepositoriesService(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RepositoriesService.class);
    }
}
