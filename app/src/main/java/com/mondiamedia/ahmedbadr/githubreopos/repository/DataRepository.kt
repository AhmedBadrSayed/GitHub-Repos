package com.mondiamedia.ahmedbadr.githubreopos.repository

import android.annotation.SuppressLint
import com.mondiamedia.ahmedbadr.githubreopos.api.RemoteDataSource
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import io.realm.Realm
import javax.inject.Inject

class DataRepository @Inject
constructor(private val remoteDataSource: RemoteDataSource) {

    private val realm = Realm.getDefaultInstance()

    val localRepos: List<GitRepo>
        get() = realm.where(GitRepo::class.java).findAll()

    suspend fun getRepos(): List<GitRepo>? {
        return if (localRepos.isEmpty()) {
            getRefreshRepos()
        } else localRepos
    }

    @SuppressLint("CheckResult")
    suspend fun getRefreshRepos(): List<GitRepo>? {
        val repositoriesService = remoteDataSource.createRepositoriesService()
        val response: List<GitRepo>
        return try {
            response = repositoriesService.getRepositories()
            saveReposList(response)
            response
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveReposList(gitHubRepositories: List<GitRepo>) {
        realm.executeTransaction { realm -> realm.copyToRealmOrUpdate(gitHubRepositories) }
    }

    fun deleteLocalData() {
        realm.executeTransaction { it.deleteAll() }
    }

    companion object {
        private val TAG = DataRepository::class.java.simpleName
    }
}
