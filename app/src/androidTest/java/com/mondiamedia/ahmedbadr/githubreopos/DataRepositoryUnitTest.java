package com.mondiamedia.ahmedbadr.githubreopos;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

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

    @Rule
    public InstantTaskExecutorRule instantRule = new InstantTaskExecutorRule();

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
}