package com.mondiamedia.ahmedbadr.githubreopos.api;

import javax.inject.Inject;

public class RemoteDataSource {

    private RepositoriesService repositoriesService;

    @Inject
    public RemoteDataSource(RepositoriesService repositoriesService) {
        this.repositoriesService = repositoriesService;
    }

    public RepositoriesService createRepositoriesService() {
        return repositoriesService;
    }
}
