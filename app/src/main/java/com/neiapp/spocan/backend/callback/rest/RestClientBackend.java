package com.neiapp.spocan.backend.callback.rest;

import androidx.annotation.Nullable;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RestClientBackend implements Backend {
    private static String jwt = "token";

    public static void doAuthenticate(String firebaseToken, CallbackInstance<User> callback) {
        callback.onSuccess(null);
    }

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {

    }

    @Override
    public void getAll(CallbackCollection<Initiative> callback) {
        final List<Initiative> initiativeList = new ArrayList<>(); // hacer pegada al backend para obtener initiatives
        callback.onSuccess(initiativeList);
    }

    // Ejemplo de GET
    @Override
    public void getObject(CallbackInstance<Object> callback) {
        RestPerformer perfomer = new RestPerformer(jwt);
        perfomer.get(Paths.BASE + Paths.OBJECT, new ServerEnsureResponseCallback() {
            @Override
            void doSuccess(@NotNull String serverResponse) {
                callback.onSuccess(serverResponse);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onSuccess(serverResponse);
            }
        });
    }

    // ejemplo de POST
    @Override
    public void createObject(Object o, CallbackVoid callbackVoid) {
        RestPerformer perfomer = new RestPerformer(jwt);

        perfomer.post(Paths.BASE + Paths.OBJECT, o.toString(), new ServerCallback() {
            @Override
            void success(@Nullable String serverResponse) {
                callbackVoid.onSuccess();
                System.out.println(serverResponse);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callbackVoid.onSuccess();
                System.out.println(statusCode + " - failure: " + serverResponse);
            }
        });
    }

    @Override
    public void ping() {
        RestPerformer performer = new RestPerformer(jwt);
        performer.get(Paths.BASE + Paths.PING, new ServerCallback() {
            @Override
            void success(@Nullable String serverResponse) {
                System.out.println(serverResponse);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                System.out.println(statusCode + " - failure: " + serverResponse);
            }
        });
    }
}