package com.neiapp.spocan.backend;

import com.neiapp.spocan.BuildConfig;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import java.util.ArrayList;

public interface Backend {
    static Backend getInstance(String token) {
        if (Boolean.parseBoolean(BuildConfig.test)) {
            return new MockedBackend();
        } else return null; // todo devolver RestClientBackend
    }
    void createInitiative(Initiative initiative, CallbackVoid callback);
    void getAll(CallbackCollection<Initiative> collection);
}

