package com.mondiamedia.ahmedbadr.githubreopos.local_db;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class LocalDataSource implements Repository {

    private Realm mRealm;

    @Inject
    public LocalDataSource() {
        mRealm = Realm.getDefaultInstance();
    }

    public void saveReposList(List<GitRepo> gitHubRepositories) {
        mRealm.executeTransaction(realm -> realm.copyToRealmOrUpdate(gitHubRepositories));
    }

    public List<GitRepo> getLocalRepos() {
        return mRealm.where(GitRepo.class).findAll();
    }

    public void deleteLocalData() {
        mRealm.executeTransaction(Realm::deleteAll);
    }
}
