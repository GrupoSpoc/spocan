package com.neiapp.spocan.backend;

import com.neiapp.spocan.BuildConfig;

public interface Backend {
    static Backend getInstance(String token) {
        if (Boolean.parseBoolean(BuildConfig.test)) {
            return new MockedBackend();
        } else return null; // todo devolver RestClientBackend
    }
}
