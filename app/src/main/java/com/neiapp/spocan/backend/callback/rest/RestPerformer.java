package com.neiapp.spocan.backend.callback.rest;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestPerformer {
    private static final String USER_TOKEN_HEADER = "User-Token";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    private final OkHttpClient httpClient;
    private final String jwt;


    public RestPerformer(String jwt) {
        this.jwt = jwt;
        this.httpClient = new OkHttpClient();
    }

    public void get(String url, Callback serverCallback) {
        Request request = buildGetRequest(url);
        perform(request, serverCallback);
    }

    public void post(String url, String payload, Callback serverCallback) {
        Request request = buildPostRequest(url, payload);
        perform(request, serverCallback);
    }

    private void perform(Request request, Callback serverCallback) {
        httpClient.newCall(request).enqueue(serverCallback);
    }

    private Request buildGetRequest(String url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader(USER_TOKEN_HEADER, jwt)
                .build();
    }

    private Request buildPostRequest(String url, String payload) {
        return new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse(APPLICATION_JSON_CHARSET_UTF_8), payload))
                .addHeader(USER_TOKEN_HEADER, jwt)
                .build();
    }
}
