package com.neiapp.spocan.backend;

import com.neiapp.spocan.models.Initiative;
import com.neiapp.spocan.models.InitiativeBatch;
import com.neiapp.spocan.models.InitiativeStatus;
import com.neiapp.spocan.models.User;
import com.neiapp.spocan.models.UserType;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MockedBackend implements Backend {
    public static ArrayList<Initiative> initiative_store;
    public static Map<Integer,Integer> initiative_by_status = new HashMap<>();
    public static final User USER = new User("TestUser", UserType.PERSON, false, initiative_by_status );
    public static User CURRENT_USER;

    protected MockedBackend() {
        this.initiative_by_status.put(InitiativeStatus.APPROVED.getId(),8);
        this.initiative_by_status.put(InitiativeStatus.PENDING.getId(),4);
        this.initiative_by_status.put(InitiativeStatus.REJECTED.getId(),3);
        if (initiative_store == null) {
            initiative_store = new ArrayList<>();
            initiative_store.add(new Initiative("1", InitiativeStatus.REJECTED,"[test] Iniciativa de prueba 1", MockedImagesBase64.RED_800_X_600, "TestUser-1", "2021-04-27T22:59:34","desaprobada"));
            initiative_store.add(new Initiative("2",InitiativeStatus.APPROVED, "[test] Iniciativa de prueba 2. Esta vez la descripcion es un poco mas larga.", MockedImagesBase64.BLUE_800_X_600, "TestUser-2", "2021-04-22T14:13:05",null));
            initiative_store.add(new Initiative("3", InitiativeStatus.PENDING,"[test] Iniciativa de prueba 3", MockedImagesBase64.GREEN_800_X_600, "CurrentUser", "2021-03-16T18:07:14",null));
            initiative_store.add(new Initiative("4", InitiativeStatus.APPROVED,"[test] Iniciativa de prueba 4", MockedImagesBase64.YELLOW_800_X_600, "TestUser-1", "2020-09-11T11:42:35",null));
            initiative_store.add(new Initiative("3", InitiativeStatus.REJECTED,"[test] Iniciativa de prueba 5", MockedImagesBase64.GREEN_800_X_600, "CurrentUser", "2021-03-16T18:07:12","hola2"));
            initiative_store.add(new Initiative("4", InitiativeStatus.PENDING,"[test] Iniciativa de prueba 6", MockedImagesBase64.YELLOW_800_X_600, "TestUser-1", "2020-09-11T11:42:36",null));
        }
    }


    public static void doAuthenticate(CallbackInstance<User> callback) {
        CURRENT_USER = USER;
        //callback.onFailure("test", 400);
        callback.onSuccess(CURRENT_USER);
    }

    @Override
    public void createInitiative(Initiative initiative, CallbackVoid callback, BiConsumer<Integer, String> error) {
        String assignedId = Integer.toString(initiative_store.size() + 1);
        initiative.setId(assignedId);
        initiative_store.add(initiative);
        callback.onSuccess();
    }

    @Override
    public void getAllInitiatives(LocalDateTime dateTop, boolean fromCurrentUser, CallbackInstance<InitiativeBatch> callback) {
        callback.onSuccess(new InitiativeBatch(initiative_store, false));
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
