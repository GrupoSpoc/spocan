package com.neiapp.spocan.Models;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.neiapp.spocan.util.Base64Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;


public class Initiative {
    public static final long ZONE_BS_AS_HOURS = 3;
    public static final ZoneId UTC = ZoneId.of("UTC");
    private String _id;
    private String description;
    private String image;
    private String nickname;
    private LocalDateTime date;
    private boolean isFromCurrentUser;

    public Initiative(String _id, String description, String image, String nickname, String dateStrUTC, boolean isFromCurrentUser) {
        this._id = _id;
        this.description = description;
        this.image = image;
        this.nickname = nickname;
        this.isFromCurrentUser = isFromCurrentUser;
        setDate(dateStrUTC);
    }

    public Initiative(String description, String image, boolean isFromCurrentUser) {
        this.description = description;
        this.image = image;
        this.isFromCurrentUser = isFromCurrentUser;
        this._id = null;
        this.nickname = null;
        initDate();
    }

    public Initiative(String description, Bitmap bitmap, boolean isFromCurrentUser) {
        this.description = description;
        setImage(bitmap);
        this.isFromCurrentUser = isFromCurrentUser;
        this._id = null;
        this.nickname = null;
        initDate();
    }

    public String get_id() {
        return _id;
    }

    private void initDate() {
        this.date = LocalDateTime.now(UTC);
    }

    public void setDate(String dateStrUTC) {
        this.date = LocalDateTime.parse(dateStrUTC);
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

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(Bitmap bitmap) {
        this.image = Base64Converter.bitmapToBase64(bitmap);
    }

    public Bitmap getImage() {
        return Base64Converter.base64ToBitmap(this.image);
    }

    public LocalDateTime getDateUTC() {
        return date;
    }

    public LocalDateTime getDateLocal() {
        return date.minusHours(ZONE_BS_AS_HOURS);
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
