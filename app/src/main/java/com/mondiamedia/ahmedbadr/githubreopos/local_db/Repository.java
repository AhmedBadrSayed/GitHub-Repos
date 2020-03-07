package com.mondiamedia.ahmedbadr.githubreopos.local_db;

import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import java.util.List;

public interface Repository {

    void saveReposList(List<GitRepo> gitHubRepositories);

    List<GitRepo> getLocalRepos();

    void deleteLocalData();
}
