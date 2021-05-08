package com.neiapp.spocan.backend.callback.rest;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestPerformer {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String CHARSET_UTF_8 = "charset=utf-8";
    private static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; " + CHARSET_UTF_8;
    private static final String TEXT_PLAIN_CHARSET_UTF_8 = "text/plain; " + CHARSET_UTF_8;

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
        Request request = buildPostRequestJson(url, payload);
        perform(request, serverCallback);
    }

    public void postText(String url, String payload, Callback serverCallback) {
        Request request = buildPostRequestJson(url, payload);
        perform(request, serverCallback);
    }

    private void perform(Request request, Callback serverCallback) {
        httpClient.newCall(request).enqueue(serverCallback);
    }

    private Request buildGetRequest(String url) {
        return commonRequestBuilder(url)
                .get()
                .build();
    }

    private Request buildPostRequestJson(String url, String payload) {
        return commonRequestBuilder(url)
                .post(RequestBody.create(MediaType.parse(APPLICATION_JSON_CHARSET_UTF_8), payload))
                .build();
    }

    private Request buildPostRequestText(String url, String payload) {
        return buildPostRequest(url, payload, MediaType.parse(TEXT_PLAIN_CHARSET_UTF_8));
    }

    private Request buildPostRequest(String url, String payload, MediaType mediaType) {
        return commonRequestBuilder(url)
                .post(RequestBody.create(mediaType, payload))
                .build();
    }

    private Request.Builder commonRequestBuilder(String url) {
        return new Request.Builder()
                .url(url)
                .addHeader(AUTHORIZATION_HEADER, "Bearer " + jwt);
    }
}
