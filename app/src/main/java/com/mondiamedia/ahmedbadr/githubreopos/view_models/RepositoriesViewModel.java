package com.mondiamedia.ahmedbadr.githubreopos.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;
import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository;

import java.util.List;

import javax.inject.Inject;

public class RepositoriesViewModel extends ViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<List<GitHubRepository>> mReposList;

    @Inject
    public RepositoriesViewModel(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public void init() {
        if (mReposList != null) {
            return;
        }

        mReposList = dataRepository.getRepos();
    }

    public LiveData<List<GitHubRepository>> getRepos() {
        return mReposList;
    }

    public LiveData<List<GitHubRepository>> refreshRepos() {
        mReposList = dataRepository.getRefreshRepos();
        return mReposList;
    }
}
