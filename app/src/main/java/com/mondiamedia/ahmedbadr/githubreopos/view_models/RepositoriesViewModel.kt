package com.mondiamedia.ahmedbadr.githubreopos.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo
import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesViewModel @Inject
constructor(private val dataRepository: DataRepository) : ViewModel() {
    private var _reposList = MutableLiveData<List<GitRepo>>()
    val reposList: LiveData<List<GitRepo>>?
        get() = _reposList

    fun init() {
        viewModelScope.launch {
            reposList?.value.let {
                if (!it.isNullOrEmpty()) return@launch
            }

            _reposList.value = dataRepository.getRepos()
        }
    }

    suspend fun refreshRepos() {
        _reposList.value = dataRepository.getRefreshRepos()
    }
}
