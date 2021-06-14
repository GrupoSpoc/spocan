package com.neiapp.spocan.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.neiapp.spocan.backend.ParseJsonException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class User implements Serializable {
    private String nickname;
    private UserType type;
    private boolean admin;
    private Map<Integer, Integer> initiativesByStatus;

    public User(String nickname, UserType type) {
        this.nickname = nickname;
        this.type = type;
        this.admin = false;
        this.initiativesByStatus = new HashMap<>();

    }

    public User(String nickname, UserType type, boolean admin, Map<Integer, Integer> initiativesByStatus) {
        this.nickname = nickname;
        this.type = type;
        this.admin = admin;
        this.initiativesByStatus = initiativesByStatus;
    }

    public boolean isAdmin() {
        return admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Map<Integer, Integer> getInitiativesByStatus() {
        return initiativesByStatus;
    }

    public void setInitiativesByStatus(Map<Integer, Integer> initiativesByStatus) {
        this.initiativesByStatus = initiativesByStatus;
    }

    public String toJson() throws ParseJsonException {
        try {
            JsonObject json = new JsonObject();

            json.addProperty("nickname", this.nickname);
            json.addProperty("type_id", this.type.getId());

            return json.toString();
        } catch (Exception e) {
            String message = "failed to convert user to json" + e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
        }
    }

    public static User convertJson(String jsonToTransform) throws ParseJsonException {
        try {

            JSONObject jsonObject = new JSONObject(jsonToTransform);

            final String nickname = jsonObject.getString("nickname");
            final int typeId = jsonObject.getInt("type_id");
            final boolean admin = jsonObject.getBoolean("admin");
            JSONObject statusObject = jsonObject.getJSONObject("initiatives_by_status");
            Map<Integer, Integer> initiativesByStatus = convertJSONObjectToStatus(statusObject);

            return new User(nickname, UserType.fromIdOrElseThrow(typeId),  admin, initiativesByStatus);
        } catch (Exception e) {
            String message = "failed to convert json to user" + e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
        }
    }

    private static Map<Integer, Integer> convertJSONObjectToStatus(JSONObject statusObject) throws JSONException {

        Map<Integer, Integer> result = new HashMap<>();

        result.put(InitiativeStatus.PENDING.getId(), statusObject.getInt(String.valueOf(InitiativeStatus.PENDING.getId())));
        result.put(InitiativeStatus.APPROVED.getId(), statusObject.getInt(String.valueOf(InitiativeStatus.APPROVED.getId())));
        result.put(InitiativeStatus.REJECTED.getId(), statusObject.getInt(String.valueOf(InitiativeStatus.REJECTED.getId())));
        return result;
    }

    public int getAmountOfInitiativesByStatus(int status) {
        Integer ammountOfInitiatives = initiativesByStatus.get(status);

        return ammountOfInitiatives != null? ammountOfInitiatives : 0;
    }

    public int getAmountOfInitiatives(){
        return initiativesByStatus.get(InitiativeStatus.PENDING.getId()) + initiativesByStatus.get(InitiativeStatus.APPROVED.getId()) + initiativesByStatus.get(InitiativeStatus.REJECTED.getId());
    }
}
