package com.neiapp.spocan.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenInfo {

    private String jwt;
    private User user;

    public TokenInfo(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static TokenInfo convertJson(String jsonToTransform) {
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);
            final String jwt = jsonObject.getString("jwt");
            final User user;

            if (jsonObject.has("user")) {
                user = User.convertJson(jsonObject.getString("user"));
            } else {
                user = null;
            }

            return new TokenInfo(jwt, user);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
