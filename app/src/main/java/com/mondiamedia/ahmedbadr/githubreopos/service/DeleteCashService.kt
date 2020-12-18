package com.mondiamedia.ahmedbadr.githubreopos.service


import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

import io.realm.Realm

class DeleteCashService : JobService() {

    private lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        realm = Realm.getDefaultInstance()
    }

    override fun onStartJob(job: JobParameters): Boolean {
        realm.executeTransaction { it.deleteAll() }
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
}
