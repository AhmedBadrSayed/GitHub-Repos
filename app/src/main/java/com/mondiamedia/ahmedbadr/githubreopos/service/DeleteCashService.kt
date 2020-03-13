package com.mondiamedia.ahmedbadr.githubreopos.service


import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

import io.realm.Realm

class DeleteCashService : JobService() {

    private lateinit var mRealm: Realm

    override fun onCreate() {
        super.onCreate()
        mRealm = Realm.getDefaultInstance()
    }

    override fun onStartJob(job: JobParameters): Boolean {
        mRealm.executeTransaction { it.deleteAll() }
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
}
