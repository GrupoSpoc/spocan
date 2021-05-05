package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String nickname;
    private UserType type;
    private int amountOfInitiatives;
    private boolean admin;

    public User(String nickname, UserType type) {
        this.nickname = nickname;
        this.type = type;
        this.amountOfInitiatives = 0;
        this.admin = false;

    }
    public User(String nickname, UserType type, int amountOfInitiatives, boolean admin){
        this.nickname = nickname;
        this.type = type;
        this.admin = admin;
        this.amountOfInitiatives = amountOfInitiatives;
    }

    public boolean isAdmin() {return admin;}

    public int getAmountOfInitiatives() {
        return amountOfInitiatives;
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

    public String toJson() {
        JsonObject json = new JsonObject();

        json.addProperty("nickname", this.nickname);
        json.addProperty("type", this.type.getId());

        return json.toString();
    }

    public static User convertJson(String jsonToTransform) {
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);
            final String nickname = jsonObject.getString("nickname");
            final int typeId = jsonObject.getInt("type");
            final boolean admin = jsonObject.getBoolean("admin");
            final int amountOfInitiatives = jsonObject.getInt("amount_of_initiatives");
            return new User(nickname, UserType.fromIdOrElseThrow(typeId),amountOfInitiatives, admin);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
