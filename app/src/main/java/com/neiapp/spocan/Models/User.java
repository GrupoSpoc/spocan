package com.neiapp.spocan.Models;

import com.google.android.gms.common.providers.PooledExecutorsProvider;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String nickname;
    private UserType type;
    private int amount_inititatives;

    public User(String nickname, UserType type) {
        this.nickname = nickname;
        this.type = type;
    }
    public User(String nickname, UserType type, int amountInitiative){
        this.nickname = nickname;
        this.type = type;
        if (amountInitiative > 0){
            this.amount_inititatives = amountInitiative;
        }else{
            this.amount_inititatives = 0;
        }
    }

    public int getAmount_inititatives() {
        return amount_inititatives;
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
        json.addProperty("amount_initiatives",this.amount_inititatives);

        return json.toString();
    }

    public static User convertJson(String jsonToTransform) {
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);
            final String nickname = jsonObject.getString("nickname");
            final int typeId = jsonObject.getInt("type");
            final int amountInitiatives = jsonObject.getInt("amount_initiatives");
            return new User(nickname, UserType.fromIdOrElseThrow(typeId),amountInitiatives);
        } catch (JSONException e) {
            throw new RuntimeException(e);

        }
    }
}
