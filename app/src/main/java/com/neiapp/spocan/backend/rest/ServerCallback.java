package com.neiapp.spocan.backend.rest;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class ServerCallback implements Callback {
    private static final int INTERNAL_SERVER_ERROR = 5001;

    @Override
    public void onFailure(@NotNull Call call, IOException e) {
        failure(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @Override
    public void onResponse(@NotNull Call call, Response response) throws IOException {
        final String message = response.body() != null ? response.body().string() : null;
        if (response.isSuccessful() && response.body() != null) {
            success(message);
        } else {
            failure(response.code(), message);
        }
    }

    abstract void success(@Nullable String serverResponse);
    abstract void failure(int statusCode, @Nullable String serverResponse);
}
