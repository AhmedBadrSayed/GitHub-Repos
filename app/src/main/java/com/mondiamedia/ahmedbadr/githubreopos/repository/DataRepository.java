package com.mondiamedia.ahmedbadr.githubreopos.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.mondiamedia.ahmedbadr.githubreopos.api.RemoteDataSource;
import com.mondiamedia.ahmedbadr.githubreopos.api.RepositoriesService;
import com.mondiamedia.ahmedbadr.githubreopos.local_db.LocalDataSource;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private MutableLiveData<List<GitRepo>> mRepositoriesList = new MutableLiveData<>();
    private RemoteDataSource mRemoteDataSource;
    private LocalDataSource localDataSource;

    @Inject
    public DataRepository(RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public MutableLiveData<List<GitRepo>> getRepos() {
        List<GitRepo> repositoriesRealmList = localDataSource.getLocalRepos();

        mRepositoriesList.setValue(repositoriesRealmList);

        if (mRepositoriesList.getValue() == null || mRepositoriesList.getValue().isEmpty()) {
            return getRefreshRepos();
        }

        return mRepositoriesList;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<GitRepo>> getRefreshRepos() {
        RepositoriesService repositoriesService = mRemoteDataSource.createRepositoriesService();
        Observable<List<GitRepo>> call = repositoriesService.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryResponse -> {
                    Log.d(TAG, "success");
                    if (gitHubRepositoryResponse != null) {
                        mRepositoriesList.setValue(gitHubRepositoryResponse);
                        localDataSource.saveReposList(gitHubRepositoryResponse);

//                        Job deleteCashJob = mDispatcher.newJobBuilder()
//                                .setService(DeleteCashService.class)
//                                .setTag("DeleteCashService")
//                                .setReplaceCurrent(true)
//                                .setTrigger(Trigger.executionWindow(60 * 120, 60 * 120))
//                                .build();
//
//                        mDispatcher.mustSchedule(deleteCashJob);
                    }
                }, throwable -> {
                    mRepositoriesList.setValue(null);
                    Log.d(TAG, throwable.getMessage());
                });
        return mRepositoriesList;
    }
}
