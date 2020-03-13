package com.mondiamedia.ahmedbadr.githubreopos

import com.mondiamedia.ahmedbadr.githubreopos.di.ApplicationComponent
import com.mondiamedia.ahmedbadr.githubreopos.di.DaggerApplicationComponent

import io.realm.Realm
import io.realm.RealmConfiguration

class Application : android.app.Application() {
    var applicationComponent: ApplicationComponent = DaggerApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name(NAME)
                .schemaVersion(VERSION.toLong()).deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }

    companion object {

        const val NAME = "repos_db"
        const val VERSION = 0
    }
}
