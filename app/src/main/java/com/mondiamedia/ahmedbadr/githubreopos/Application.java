package com.mondiamedia.ahmedbadr.githubreopos;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class Application extends android.app.Application {

    public static final String NAME = "repos_db";
    public static final int VERSION = 0;

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
