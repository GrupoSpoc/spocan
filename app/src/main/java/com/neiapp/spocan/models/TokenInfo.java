package com.neiapp.spocan.models;

import com.neiapp.spocan.backend.ParseJsonException;

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

    public static TokenInfo convertJson(String jsonToTransform) throws ParseJsonException {
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
        } catch (Exception e) {
            String message = "failed to convert json to TokenInfo"+ e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
        }
    }
}