package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private String nickname;
    private UserType type;

    public User(String nickname, UserType type) {
        this.nickname = nickname;
        this.type = type;
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

    public static User convertJson(String jsonToTransform){
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);
            final String nickname = jsonObject.getString("nickname");
            final int typeId = jsonObject.getInt("type");
            return new User(nickname, UserType.fromIdOrElseThrow(typeId));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
