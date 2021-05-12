package com.neiapp.spocan.backend;

import com.neiapp.spocan.BuildConfig;
import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.backend.rest.RestClientBackend;

public interface Backend {
    void ping();

    void createInitiative(Initiative initiative, CallbackVoid callback);
    void getAll(CallbackCollection<Initiative> collection);
    void getUser(CallbackInstance<User> callback);
    void getObject(CallbackInstance<Object> callback);
<<<<<<< HEAD
    void createObject(Object o, CallbackVoid callbackVoid);
    void createUser(User U, CallbackVoid callbackVoid);
=======
    void createUser(User user, CallbackInstance<User> callbackUser);
>>>>>>> master

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

