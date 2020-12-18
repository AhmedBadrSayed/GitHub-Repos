package com.mondiamedia.ahmedbadr.githubreopos;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.mondiamedia.ahmedbadr.githubreopos.repository.DataRepository;
import com.mondiamedia.ahmedbadr.githubreopos.models.GitRepo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataRepositoryUnitTest {

    DataRepository mDataRepository;

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

    @Before
    public void initiate() {
//        mDataRepository = DataRepository.getInstance(InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void testEmptyDatabase() {
        Observer<List<GitRepo>> corridorObserver = gitHubRepositories -> assertEquals(gitHubRepositories.size(), 0);

//        mDataRepository.getRepos().observeForever(corridorObserver);
    }

    @Test
    public void testSaveReposListOneItem() {
        List<GitRepo> gitHubRepositories = new ArrayList<>();
        gitHubRepositories.add(new GitRepo("url", "author", "name", "avatar", "description"
                , "language", "languageColor", "stars", "forks"));
        mDataRepository.saveReposList(gitHubRepositories);

        assertEquals(gitHubRepositories.size(), mDataRepository.getLocalRepos().size());
    }

    @Test
    public void testService() throws IOException {
        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.start();

        MockResponse mockResponse = new MockResponse();
        mockResponse.setResponseCode(200);
        mockResponse.setBody("Hi!!");

        mockWebServer.enqueue(mockResponse);

        HttpUrl url = mockWebServer.url("/api/hey");

        String requestBody = getRequestResponse(new OkHttpClient(), url);

        assertEquals("Hi!!", requestBody);
    }

    private String getRequestResponse(OkHttpClient okHttpClient, HttpUrl base) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), "hi");

        Request request = new Request.Builder()
                .post(body)
                .url(base)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }

    @After
    public void tearDown() {
        mDataRepository.deleteLocalData();
    }
}