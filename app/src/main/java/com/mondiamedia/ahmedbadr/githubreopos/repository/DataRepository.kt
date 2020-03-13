package com.mondiamedia.ahmedbadr.githubreopos.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mondiamedia.ahmedbadr.githubreopos.api.RemoteDataSource
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import javax.inject.Inject

class DataRepository @Inject
constructor(private val mRemoteDataSource: RemoteDataSource) {

    private val mRepositoriesList = MutableLiveData<List<GitRepo>>()
    private val mRealm = Realm.getDefaultInstance()

    val localRepos: List<GitRepo>
        get() = mRealm.where(GitRepo::class.java).findAll()

    val repos: MutableLiveData<List<GitRepo>>
        get() {
            val repositoriesRealmList = localRepos

            mRepositoriesList.value = repositoriesRealmList

            return if (mRepositoriesList.value == null || mRepositoriesList.value!!.isEmpty()) {
                refreshRepos
            } else mRepositoriesList
        }

    val refreshRepos: MutableLiveData<List<GitRepo>>
        @SuppressLint("CheckResult")
        get() {
            val repositoriesService = mRemoteDataSource.createRepositoriesService()
            val call = repositoriesService.getRepositories()
            call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ gitHubRepositoryResponse ->
                        Log.d(TAG, "success")
                        mRepositoriesList.value = gitHubRepositoryResponse
                        saveReposList(gitHubRepositoryResponse)
                    }, { throwable ->
                        mRepositoriesList.value = null
                        Log.d(TAG, throwable.message)
                    })
            return mRepositoriesList
        }

    fun saveReposList(gitHubRepositories: List<GitRepo>) {
        mRealm.executeTransaction { realm -> realm.copyToRealmOrUpdate(gitHubRepositories) }
    }

    fun deleteLocalData() {
        mRealm.executeTransaction { it.deleteAll() }
    }

    companion object {
        private val TAG = DataRepository::class.java.simpleName
    }
}
