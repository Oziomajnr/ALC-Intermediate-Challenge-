package com.example.ogbeoziomajnr.githubprofileviewer;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by SQ-OGBE PC on 10/03/2017.
 * This class helps us make http request using unirest library
 */

public class HttpRequestHelper {
    private final OkHttpClient client = new OkHttpClient();

    public HttpRequestHelper() {
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://api.github.com//search/users?q=location:lagos language:java")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Headers responseHeaders = response.headers();
        for (int i = 0; i < responseHeaders.size(); i++) {
            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
        }

        System.out.println(response.body().string());
    }
}
