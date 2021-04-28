package com.neiapp.spocan.Models;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.neiapp.spocan.backend.exception.JsonParseCustomException;
import com.neiapp.spocan.util.Base64Converter;


public class Initiative {
    static final String MSG_ERR_PARSE_JSON = "Error al parsear el JSON";
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

    public Initiative(String title, String description, Bitmap bitmap, boolean isFromCurrentUser) {
        this.title = title;
        this.description = description;
        setImage(bitmap);
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

    public void setImage(Bitmap bitmap) {
        this.imageBase64 = Base64Converter.bitmapToBase64(bitmap);
    }

    public Bitmap getImage() {
        return Base64Converter.base64ToBitmap(this.imageBase64);
    }

    public String toJson(){
        Gson gson = new Gson();
        return  gson.toJson(this);
    }

    public  static Initiative convertJson (String jsonToTransform) throws JsonParseCustomException {
        try {
            Gson gson = new Gson();
            return gson.fromJson(jsonToTransform, Initiative.class);
        }catch (Exception e){
            throw new JsonParseCustomException(Initiative.class.getName(), MSG_ERR_PARSE_JSON);
        }
    }
}
