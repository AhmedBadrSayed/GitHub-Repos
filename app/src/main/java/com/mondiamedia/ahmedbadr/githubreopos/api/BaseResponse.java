package com.mondiamedia.ahmedbadr.githubreopos.api;

public class BaseResponse<S> {

    private S[] results;

    public S[] getResults() {
        return results;
    }

    public void setResults(S[] results) {
        this.results = results;
    }

}
