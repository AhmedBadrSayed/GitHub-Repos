package com.mondiamedia.ahmedbadr.githubreopos.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository

import javax.inject.Inject

class RepositoriesViewModel @Inject
constructor(private val dataRepository: DataRepository) : ViewModel() {
    private var mReposList: MutableLiveData<List<GitRepo>>? = null

    fun init() {
        if (mReposList != null) {
            return
        }

        mReposList = dataRepository.repos
    }

    val repos: LiveData<List<GitRepo>>?
        get() = mReposList

    fun refreshRepos(): LiveData<List<GitRepo>>? {
        mReposList = dataRepository.refreshRepos
        return mReposList
    }
}
