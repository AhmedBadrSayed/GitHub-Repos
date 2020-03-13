package com.mondiamedia.ahmedbadr.githubreopos.api

import javax.inject.Inject

class RemoteDataSource @Inject
constructor(private val repositoriesService: RepositoriesService) {

    fun createRepositoriesService(): RepositoriesService {
        return repositoriesService
    }
}
