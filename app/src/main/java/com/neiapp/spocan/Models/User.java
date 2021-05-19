package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;
import com.neiapp.spocan.backend.ParseJsonException;

import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
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

    public String toJson() throws ParseJsonException {
       try {
            JsonObject json = new JsonObject();

            json.addProperty("nickname", this.nickname);
            json.addProperty("type_id", this.type.getId());

            return json.toString();
        }catch (Exception e){
           String message = "failed to convert user to json"+ e.getMessage();
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
            final int amountOfInitiatives = jsonObject.getInt("amount_of_initiatives");
            return new User(nickname, UserType.fromIdOrElseThrow(typeId),amountOfInitiatives, admin);
        } catch (Exception e) {
           String message = "failed to convert json to user" + e.getMessage();
           System.out.println(message);
           throw new ParseJsonException(message);
        }
    }
}
