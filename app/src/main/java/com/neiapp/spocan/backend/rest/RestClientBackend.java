package com.neiapp.spocan.backend.rest;

import androidx.annotation.Nullable;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.TokenInfo;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.backend.Backend;
import com.neiapp.spocan.backend.ParseJsonException;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.backend.callback.Fallible;
import com.neiapp.spocan.backend.rest.query.QueryParam;
import com.neiapp.spocan.backend.rest.query.QueryParamsBuilder;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

import static com.neiapp.spocan.backend.rest.HTTPCodes.ERROR_PARSE_JSON;


public class RestClientBackend implements Backend {
    private static String jwt = "token";
    private final RestPerformer performer;


    public RestClientBackend() {
        this.performer = new RestPerformer(jwt);
    }

    public static void doAuthenticate(String firebaseToken, CallbackInstance<User> callback) {
        RestPerformer.postTextUnauthorizable(Paths.BASE + Paths.AUTHENTICATE, firebaseToken, new ServerEnsureResponseCallback() {
            @Override
            void doSuccess(@NotNull String serverResponse) {
                executeJsonAction(() -> {
                    TokenInfo tokenInfo = TokenInfo.convertJson(serverResponse);
                    jwt = tokenInfo.getJwt();
                    callback.onSuccess(tokenInfo.getUser());
                }, callback);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onFailure(serverResponse, statusCode);
            }

        });
    }

    @Override
    public void createUser(User user, CallbackInstance<User> callbackUser) {
        executeJsonAction(() -> performer.post(Paths.BASE + Paths.USER, user.toJson(), new ServerEnsureResponseCallback() {
            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callbackUser.onFailure(serverResponse, statusCode);
                System.out.println(statusCode + "-Failure-" + serverResponse);
            }

            @Override
            void doSuccess(@NotNull String serverResponse) {
                executeJsonAction(() -> callbackUser.onSuccess(User.convertJson(serverResponse)), callbackUser);
            }
        }), callbackUser);
    }

    @Override
    public void getUser(CallbackInstance<User> callback) {
        executeJsonAction(() -> performer.get(Paths.BASE + Paths.USER, new ServerEnsureResponseCallback() {
            @Override
            void doSuccess(@NotNull String serverResponse) {
                executeJsonAction(() -> callback.onSuccess(User.convertJson(serverResponse)), callback);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onFailure(serverResponse, statusCode);
            }
        }), callback);
    }


    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {
        executeJsonAction(() -> performer.post(Paths.BASE + Paths.INITIATIVE, initiative.toJson(), new ServerCallback() {
            @Override
            void success(@Nullable String serverResponse) {
                callback.onSuccess();
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onFailure(serverResponse, statusCode);
            }
        }), callback);
    }


    @Override
    public void logOut(CallbackVoid callback) {
        jwt = null;
        callback.onSuccess();
    }


    @Override
    public void getAllInitiatives(LocalDateTime dateTop, boolean fromCurrentUser, int offset, CallbackCollection<Initiative> callback) {
        QueryParamsBuilder queryParamsBuilder = new QueryParamsBuilder();
        queryParamsBuilder
                .withParam(QueryParam.ORDER, "1")
                .withParam(QueryParam.LIMIT, "2")
                .withParam(QueryParam.STATUS, "2");
        if (fromCurrentUser) {
                queryParamsBuilder.withParam(QueryParam.CURRENT_USER, String.valueOf(fromCurrentUser));
                queryParamsBuilder.withParam(QueryParam.OFFSET, String.valueOf(offset));
                queryParamsBuilder.withParam(QueryParam.STATUS, "1"); // sumamos las pendientes

        }

        if (dateTop != null) {
               queryParamsBuilder.withParam(QueryParam.DATE_TOP, dateTop.toString());
        }

        performer.get(Paths.BASE + Paths.INITIATIVE + Paths.ALL, queryParamsBuilder.build(), new ServerEnsureResponseCallback() {
            @Override
            void doSuccess(@NotNull String serverResponse) {
                executeJsonAction(() -> callback.onSuccess(Initiative.convertJsonList(serverResponse)), callback);
            }

            @Override
            void failure(int statusCode, @Nullable String serverResponse) {
                callback.onFailure(serverResponse, statusCode);
            }
        });
    }


    // Recibe una acción que puede fallar por la nueva excepción y un Fallible (CallbackInstance - CallbackCollection)
    private static void executeJsonAction(FallibleExecutable executable, Fallible fallible) {
        try {
            executable.execute();
        } catch (Exception e) {
            fallible.onFailure("", ERROR_PARSE_JSON.getCode());
        }
    }

    @FunctionalInterface
    private interface FallibleExecutable {
        void execute() throws ParseJsonException;
    }


}
