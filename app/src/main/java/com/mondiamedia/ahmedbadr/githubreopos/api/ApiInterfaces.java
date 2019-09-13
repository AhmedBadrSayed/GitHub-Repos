package com.mondiamedia.ahmedbadr.githubreopos.api;

import com.mondiamedia.ahmedbadr.githubreopos.models.Repository;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterfaces {

    @GET("/repositories")
    Observable<BaseResponse<Repository>> getMovies();
}
