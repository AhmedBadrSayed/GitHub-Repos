package com.mondiamedia.ahmedbadr.githubreopos.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String BASE_URL = "https://github-trending-api.now.sh";
    private Retrofit retrofit;

    public RestClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <S> S createService(Class<S> serviceInterface) {
        return retrofit.create(serviceInterface);
    }
}
