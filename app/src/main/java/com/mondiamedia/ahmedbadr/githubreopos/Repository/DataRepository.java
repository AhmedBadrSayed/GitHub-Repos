package com.mondiamedia.ahmedbadr.githubreopos.Repository;

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
    private MutableLiveData<List<GitHubRepository>> mReposList = new MutableLiveData<>();
    private List<GitHubRepository> mGitHubRepositoryRealmList;
    private RestClient mRestClient;
    private Realm realm;
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
        realm = Realm.getDefaultInstance();
    }

    public MutableLiveData<List<GitHubRepository>> getRepos() {
        realm.beginTransaction();
        mGitHubRepositoryRealmList = realm.where(GitHubRepository.class).findAll();
        realm.commitTransaction();

        mReposList.setValue(mGitHubRepositoryRealmList);

        if (mReposList.getValue() == null || mReposList.getValue().size() == 0) {
            refreshRepos();
        }

        return mReposList;
    }

    @SuppressLint("CheckResult")
    public void refreshRepos() {
        ApiInterfaces apiInterfaces = mRestClient.createService(ApiInterfaces.class);
        Observable<List<GitHubRepository>> call =
                apiInterfaces.getRepositories();
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(gitHubRepositoryResponse -> {
                    Log.d(TAG, "success");
                    if (gitHubRepositoryResponse != null) {
                        mReposList.setValue(gitHubRepositoryResponse);
                        saveReposList(gitHubRepositoryResponse);

                        Job deleteCashJob = mDispatcher.newJobBuilder()
                                .setService(DeleteCashService.class)
                                .setTag("DeleteCashService")
                                .setTrigger(Trigger.executionWindow(60 * 120, 60 * 120))
                                .build();

                        mDispatcher.mustSchedule(deleteCashJob);
                    }
                }, throwable -> {
                    mReposList.setValue(null);
                    Log.d(TAG, throwable.getMessage());
                });
    }

    private void saveReposList(List<GitHubRepository> gitHubRepositories) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(gitHubRepositories);
        realm.commitTransaction();
    }
}
