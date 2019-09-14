package com.mondiamedia.ahmedbadr.githubreopos.Repository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.mondiamedia.ahmedbadr.githubreopos.api.ApiInterfaces;
import com.mondiamedia.ahmedbadr.githubreopos.api.RestClient;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private MutableLiveData<List<GitHubRepository>> mReposList = new MutableLiveData<>();
    private RestClient mRestClient;

    private static DataRepository sDataRepository;

    public static DataRepository getInstance() {
        if (sDataRepository == null) {
            sDataRepository = new DataRepository();
        }
        return sDataRepository;
    }

    private DataRepository() {
        mRestClient = new RestClient();
    }

    public MutableLiveData<List<GitHubRepository>> getRepos() {
        ApiInterfaces apiInterfaces = mRestClient.createService(ApiInterfaces.class);
        Observable<List<GitHubRepository>> call =
                apiInterfaces.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryBaseResponse -> {
                    Log.d(TAG, "success");
                    if (gitHubRepositoryBaseResponse != null) {
                        mReposList.setValue(gitHubRepositoryBaseResponse);
                    }
                }, throwable -> {
                    mReposList.setValue(null);
                    Log.d(TAG, throwable.getMessage());
                });

        return mReposList;
    }
}
