package com.mondiamedia.ahmedbadr.githubreopos.api

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo

import io.reactivex.Observable
import retrofit2.http.GET

interface RepositoriesService {

    @GET("/repositories")
    fun getRepositories(): Observable<List<GitRepo>>
}
