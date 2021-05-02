package com.neiapp.spocan.backend;

import com.neiapp.spocan.BuildConfig;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;

public interface Backend {
    void createInitiative(Initiative initiative, CallbackVoid callback);
    void getAll(CallbackCollection<Initiative> collection);

    static Backend getInstance() {
        if (test()) {
            return new MockedBackend();
        } else {
           return new RestClientBackend();
        }
    }

    static void authenticate(String firebaseToken, CallbackInstance<User> callback) {
        if (test()) {
            MockedBackend.doAuthenticate(callback);
        } else RestClientBackend.doAuthenticate(firebaseToken, callback);
    }

    static boolean test() {
        return Boolean.parseBoolean(BuildConfig.test);
    }
}

