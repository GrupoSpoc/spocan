package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import java.util.ArrayList;
import java.util.List;

public class RestClientBackend implements Backend { // todo implementar Backend
    private static String jwt;


    public RestClientBackend() {

    }

    public static void doAuthenticate(String firebaseToken, CallbackInstance<User> callback) {

    }

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {

    }

    @Override
    public void getAll(CallbackCollection<Initiative> callback) {
        final List<Initiative> initiativeList = new ArrayList<>(); // hacer pegada al backend para obtener initiatives
        callback.onSuccess(initiativeList);
    }
}
