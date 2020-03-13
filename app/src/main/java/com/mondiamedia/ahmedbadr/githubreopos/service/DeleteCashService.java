package com.mondiamedia.ahmedbadr.githubreopos.service;


import androidx.annotation.NonNull;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import io.realm.Realm;

public class DeleteCashService extends JobService {

    private Realm mRealm;

    @Override
    public void onCreate() {
        super.onCreate();
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public boolean onStartJob(@NonNull JobParameters job) {
        mRealm.executeTransaction(Realm::deleteAll);
        return false;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return false;
    }
}
