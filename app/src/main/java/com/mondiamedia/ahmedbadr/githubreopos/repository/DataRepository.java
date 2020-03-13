package com.mondiamedia.ahmedbadr.githubreopos.repository;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.mondiamedia.ahmedbadr.githubreopos.api.RemoteDataSource;
import com.mondiamedia.ahmedbadr.githubreopos.api.RepositoriesService;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private MutableLiveData<List<GitHubRepository>> mRepositoriesList = new MutableLiveData<>();
    private RemoteDataSource mRemoteDataSource;
    private Realm mRealm;

    @Inject
    public DataRepository(RemoteDataSource remoteDataSource) {
        this.mRemoteDataSource = remoteDataSource;
        mRealm = Realm.getDefaultInstance();
    }

    public MutableLiveData<List<GitHubRepository>> getRepos() {
        List<GitHubRepository> repositoriesRealmList = getLocalRepos();

        mRepositoriesList.setValue(repositoriesRealmList);

        if (mRepositoriesList.getValue() == null || mRepositoriesList.getValue().isEmpty()) {
            return getRefreshRepos();
        }

        return mRepositoriesList;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<GitHubRepository>> getRefreshRepos() {
        RepositoriesService repositoriesService = mRemoteDataSource.createRepositoriesService();
        Observable<List<GitHubRepository>> call = repositoriesService.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryResponse -> {
                    Log.d(TAG, "success");
                    if (gitHubRepositoryResponse != null) {
                        mRepositoriesList.setValue(gitHubRepositoryResponse);
                        saveReposList(gitHubRepositoryResponse);

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

    public void saveReposList(List<GitHubRepository> gitHubRepositories) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(gitHubRepositories));
    }

    public List<GitHubRepository> getLocalRepos() {
        return mRealm.where(GitHubRepository.class).findAll();
    }

    public void deleteLocalData() {
        mRealm.executeTransaction(Realm::deleteAll);
    }
}
