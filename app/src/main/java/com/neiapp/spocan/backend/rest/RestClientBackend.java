package com.neiapp.spocan.backend.rest;

import androidx.annotation.Nullable;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.TokenInfo;
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
    private final RestPerformer performer;


    public RestClientBackend() {
        this.performer = new RestPerformer(jwt);
    }

    //POST
    public static void doAuthenticate(String firebaseToken, CallbackInstance<User> callback) {
        RestPerformer testRestPerformer = new RestPerformer(" testToken");
        testRestPerformer.post(Paths.BASE_TEST + Paths.AUTHENTICATE, firebaseToken, new ServerEnsureResponseCallback() {
            @Override
            void doSuccess(@NotNull String serverResponse) {
                TokenInfo tokenInfo = TokenInfo.convertJson(serverResponse);
                jwt = tokenInfo.getJwt();
                callback.onSuccess(tokenInfo.getUser());
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onFailure(serverResponse, statusCode);
            }
        });
    }

    //POST
    @Override
    public void createUser(User user, CallbackInstance<User>  callbackUser) {
        performer.post(Paths.BASE + Paths.USER, user.toString(), new ServerEnsureResponseCallback() {
            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callbackUser.onFailure(serverResponse, statusCode);
                System.out.println(statusCode + "-Failure-" + serverResponse);
            }

            @Override
            void doSuccess(@NotNull String serverResponse) {
                callbackUser.onSuccess(User.convertJson(serverResponse));
                System.out.println(serverResponse);
            }
        });

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
        performer.get(Paths.BASE + Paths.OBJECT, new ServerEnsureResponseCallback() {
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
    public void createObject(Object o, CallbackVoid callbackVoid) {
        performer.post(Paths.BASE + Paths.OBJECT, o.toString(), new ServerCallback() {
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
