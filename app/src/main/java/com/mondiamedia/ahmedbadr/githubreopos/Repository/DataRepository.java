package com.mondiamedia.ahmedbadr.githubreopos.Repository;

import android.arch.lifecycle.LiveData;
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

    private MutableLiveData<List<GitHubRepository>> mReposList = new MutableLiveData<>();
    private RestClient restClient;

    private static DataRepository sDataRepository;

    public static DataRepository getInstance() {
        if (sDataRepository == null) {
            sDataRepository = new DataRepository();
        }
        return sDataRepository;
    }

    private DataRepository() {
        restClient = new RestClient();
    }

    public MutableLiveData<List<GitHubRepository>> getRepos() {
        Log.d("DataRepository  " , "Call");
        ApiInterfaces apiInterfaces = restClient.createService(ApiInterfaces.class);
        Observable<List<GitHubRepository>> call =
                apiInterfaces.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryBaseResponse -> {
                    Log.d("DataRepository  " , gitHubRepositoryBaseResponse.toString());
                    if (gitHubRepositoryBaseResponse != null) {
                        mReposList.setValue(gitHubRepositoryBaseResponse);
                    }
                }, throwable -> {
                    mReposList.setValue(null);
                    Log.d("DataRepository  " , throwable.getMessage());
                });

        return mReposList;
    }
}
