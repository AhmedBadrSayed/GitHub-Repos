package com.mondiamedia.ahmedbadr.githubreopos.repository;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Trigger;
import com.mondiamedia.ahmedbadr.githubreopos.api.ApiInterfaces;
import com.mondiamedia.ahmedbadr.githubreopos.api.RestClient;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;
import com.mondiamedia.ahmedbadr.githubreopos.service.DeleteCashService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private MutableLiveData<List<GitHubRepository>> mRepositoriesList = new MutableLiveData<>();
    private List<GitHubRepository> mRepositoriesRealmList;
    private RestClient mRestClient;
    private Realm mRealm;
    private FirebaseJobDispatcher mDispatcher;

    private static DataRepository sDataRepository;

    public static DataRepository getInstance(Context context) {
        if (sDataRepository == null) {
            sDataRepository = new DataRepository(context);
        }
        return sDataRepository;
    }

    private DataRepository(Context context) {
        mRestClient = new RestClient();
        mDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        mRealm = Realm.getDefaultInstance();
    }

    public MutableLiveData<List<GitHubRepository>> getRepos() {
        mRepositoriesRealmList = getLocalRepos();

        mRepositoriesList.setValue(mRepositoriesRealmList);

        if (mRepositoriesList.getValue() == null || mRepositoriesList.getValue().size() == 0) {
            return getRefreshRepos();
        }

        return mRepositoriesList;
    }

    @SuppressLint("CheckResult")
    public MutableLiveData<List<GitHubRepository>> getRefreshRepos() {
        ApiInterfaces apiInterfaces = mRestClient.createService(ApiInterfaces.class);
        Observable<List<GitHubRepository>> call =
                apiInterfaces.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryResponse -> {
                    Log.d(TAG, "success");
                    if (gitHubRepositoryResponse != null) {
                        mRepositoriesList.setValue(gitHubRepositoryResponse);
                        saveReposList(gitHubRepositoryResponse);

                        Job deleteCashJob = mDispatcher.newJobBuilder()
                                .setService(DeleteCashService.class)
                                .setTag("DeleteCashService")
                                .setReplaceCurrent(true)
                                .setTrigger(Trigger.executionWindow(60 * 120, 60 * 120))
                                .build();

                        mDispatcher.mustSchedule(deleteCashJob);
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

    public void deletLocalData() {
        mRealm.executeTransaction(realm -> realm.deleteAll());
    }
}
