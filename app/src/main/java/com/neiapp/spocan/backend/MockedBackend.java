package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.User;
import com.neiapp.spocan.Models.UserType;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;


import java.util.ArrayList;

public class MockedBackend implements Backend {
    public static ArrayList<Initiative> initiative_store;
    public static final User USER = new User("TestUser", UserType.PERSON, 550, false);
    public static User CURRENT_USER;

    protected MockedBackend() {
        if (initiative_store == null) {
            initiative_store = new ArrayList<>();
            initiative_store.add(new Initiative("1", "[test] Iniciativa de prueba 1", MockedImagesBase64.RED_800_X_600, "TestUser-1", "2021-04-27T22:59:34", false));
            initiative_store.add(new Initiative("2", "[test] Iniciativa de prueba 2. Esta vez la descripcion es un poco mas larga.", MockedImagesBase64.BLUE_800_X_600, "TestUser-2", "2021-04-22T14:13:05", false));
            initiative_store.add(new Initiative("3", "[test] Iniciativa de prueba 3", MockedImagesBase64.GREEN_800_X_600, "CurrentUser", "2021-03-16T18:07:14", true));
            initiative_store.add(new Initiative("4", "[test] Iniciativa de prueba 4", MockedImagesBase64.YELLOW_800_X_600, "TestUser-1", "2020-09-11T11:42:35", false));
        }
    }

    public static void doAuthenticate(CallbackInstance<User> callback) {
        CURRENT_USER = USER;
        callback.onFailure("test", 400);
    }

    @Override
    public void ping() {
        System.out.println("Mocked pong");
    }

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback) {
        String assignedId = Integer.toString(initiative_store.size() + 1);
        initiative.setId(assignedId);
        initiative_store.add(initiative);
        callback.onSuccess();
    }

    @Override
    public void getAll(CallbackCollection<Initiative> collection) {
        collection.onSuccess(initiative_store);
    }

    @Override
    public void getObject(CallbackInstance<Object> callback) {
        callback.onSuccess(null);
    }

    public void createObject(Object o, CallbackVoid callbackVoid) {
        callbackVoid.onSuccess();
    }

    @Override
    public void logOut(CallbackVoid callback) {
        CURRENT_USER = null;
        callback.onSuccess();
    }

    @Override
    public void createUser(User user, CallbackInstance<User> callbackUser) {
        user.setNickname(user.getNickname());
        user.setType(user.getType());
        callbackUser.onSuccess(user);
    }

    public static void resetStorage() {
        if (initiative_store != null) initiative_store.clear();
    }

    public void getUser(CallbackInstance<User> callback){
        callback.onSuccess(CURRENT_USER);
    }


}
