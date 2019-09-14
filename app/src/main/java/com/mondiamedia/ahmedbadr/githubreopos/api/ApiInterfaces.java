package com.mondiamedia.ahmedbadr.githubreopos.api;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiInterfaces {

    @GET("/repositories")
    Observable<List<GitHubRepository>> getRepositories();
}
