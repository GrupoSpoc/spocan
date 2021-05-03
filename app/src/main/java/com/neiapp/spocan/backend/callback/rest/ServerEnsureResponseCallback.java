package com.neiapp.spocan.backend.callback.rest;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public abstract class ServerEnsureResponseCallback extends ServerCallback {
    private static final int RESPONSE_IS_NULL_CODE = 4001;

    @Override
    void success(@Nullable String serverResponse) {
        if (serverResponse == null) failure(RESPONSE_IS_NULL_CODE, "server answered but the response was null");
        else doSuccess(serverResponse);
    }

    abstract void doSuccess(@NotNull String serverResponse);
}
