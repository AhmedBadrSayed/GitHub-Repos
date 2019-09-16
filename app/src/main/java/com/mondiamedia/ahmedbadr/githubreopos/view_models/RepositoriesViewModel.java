package com.mondiamedia.ahmedbadr.githubreopos.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import java.util.List;

public class RepositoriesViewModel extends AndroidViewModel {

    private MutableLiveData<List<GitHubRepository>> mReposList;
    private DataRepository dataRepository;

    public RepositoriesViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        if (mReposList != null) {
            return;
        }

        dataRepository = DataRepository.getInstance(getApplication());
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
