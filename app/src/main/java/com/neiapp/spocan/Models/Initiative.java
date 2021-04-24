package com.neiapp.spocan.Models;

import com.google.gson.Gson;


public class Initiative {

    private String id;
    private String title;
    private String description;
    private String imageBase64;
    private String nickName;
    private boolean isFromCurrentUser;

    public Initiative(String id, String title, String description, String imageBase64, String nickName, boolean isFromCurrentUser) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.nickName = nickName;
        this.isFromCurrentUser = isFromCurrentUser;
    }

    public Initiative(String title, String description, String imageBase64, boolean isFromCurrentUser) {
        this.title = title;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.isFromCurrentUser = isFromCurrentUser;
        this.id = null;
        this.nickName = null;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public String getNickName() {
        return nickName;
    }

    public boolean isFromCurrentUser() {
        return isFromCurrentUser;
    }

     public String toJson(){
     Gson gson = new Gson();
         return  gson.toJson(this);
     }
     public  static Initiative convertJson (String jsonToTransform){
       Gson gson = new Gson();
         return  gson.fromJson(jsonToTransform, Initiative.class);
     }

}
