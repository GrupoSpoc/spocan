package com.neiapp.spocan.Models;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.neiapp.spocan.util.Base64Converter;


public class Initiative {
    private String _id;
    private String description;
    private String image;
    private String nickname;
    private boolean isFromCurrentUser;

    public Initiative(String _id, String description, String image, String nickname, boolean isFromCurrentUser) {
        this._id = _id;
        this.description = description;
        this.image = image;
        this.nickname = nickname;
        this.isFromCurrentUser = isFromCurrentUser;
    }

    public Initiative(String description, String image, boolean isFromCurrentUser) {
        this.description = description;
        this.image = image;
        this.isFromCurrentUser = isFromCurrentUser;
        this._id = null;
        this.nickname = null;
    }

    public Initiative(String description, Bitmap bitmap, boolean isFromCurrentUser) {
        this.description = description;
        setImage(bitmap);
        this.isFromCurrentUser = isFromCurrentUser;
        this._id = null;
        this.nickname = null;
    }

    public String get_id() {
        return _id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageBase64() {
        return image;
    }

    public String getNickname() {
        return nickname;
    }

    public boolean isFromCurrentUser() {
        return isFromCurrentUser;
    }

    public void setImage(Bitmap bitmap) {
        this.image = Base64Converter.bitmapToBase64(bitmap);
    }

    public Bitmap getImage() {
        return Base64Converter.base64ToBitmap(this.image);
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
