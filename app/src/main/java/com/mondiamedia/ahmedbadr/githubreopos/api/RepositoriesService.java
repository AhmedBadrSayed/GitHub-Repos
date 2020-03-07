package com.mondiamedia.ahmedbadr.githubreopos.api;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface RepositoriesService {

    @GET("/repositories")
    Observable<List<GitRepo>> getRepositories();
}
