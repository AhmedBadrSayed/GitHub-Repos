package com.mondiamedia.ahmedbadr.githubreopos.service;


import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitHubRepository;

import io.realm.Realm;

public class DeleteCashService extends JobService {

    private Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
        realm.beginTransaction();
        realm.delete(GitHubRepository.class);
        realm.commitTransaction();
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }
}
