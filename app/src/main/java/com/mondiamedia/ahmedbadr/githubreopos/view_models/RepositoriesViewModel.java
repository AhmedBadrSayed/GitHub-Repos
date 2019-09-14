package com.mondiamedia.ahmedbadr.githubreopos.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.mondiamedia.ahmedbadr.githubreopos.Repository.DataRepository;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import java.util.List;

public class RepositoriesViewModel extends ViewModel {

    private MutableLiveData<List<GitHubRepository>> mReposList;
    private DataRepository dataRepository;

    public void init() {
        if (mReposList != null) {
            return;
        }
        dataRepository = DataRepository.getInstance();
        mReposList = dataRepository.getRepos();

    }

    public LiveData<List<GitHubRepository>> getRepos() {
        return mReposList;
    }
}
