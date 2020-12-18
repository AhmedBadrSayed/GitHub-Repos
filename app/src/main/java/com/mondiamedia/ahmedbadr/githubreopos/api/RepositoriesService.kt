package com.mondiamedia.ahmedbadr.githubreopos.api

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo

import retrofit2.http.GET

interface RepositoriesService {

    @GET("/repositories")
    suspend fun getRepositories(): List<GitRepo>
}
