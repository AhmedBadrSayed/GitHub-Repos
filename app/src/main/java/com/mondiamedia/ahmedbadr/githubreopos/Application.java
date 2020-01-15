package com.mondiamedia.ahmedbadr.githubreopos;

import com.mondiamedia.ahmedbadr.githubreopos.di.ApplicationComponent;
import com.mondiamedia.ahmedbadr.githubreopos.di.DaggerApplicationComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Application extends android.app.Application {

    public static final String NAME = "repos_db";
    public static final int VERSION = 0;
    public ApplicationComponent applicationComponent = DaggerApplicationComponent.create();

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(NAME)
                .schemaVersion(VERSION).deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
