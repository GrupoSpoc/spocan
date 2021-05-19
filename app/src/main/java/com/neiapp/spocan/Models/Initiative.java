package com.neiapp.spocan.Models;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonObject;
import com.neiapp.spocan.backend.ParseJsonException;
import com.neiapp.spocan.util.Base64Converter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.O)
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

    public String getId() {
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
        this._id = id;
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

    public String toJson() throws ParseJsonException {
        try {
            JsonObject json = new JsonObject();

            json.addProperty("description", this.description);
            json.addProperty("image", this.image);
            json.addProperty("date", this.date.toString());

            return json.toString();
        }catch (Exception e){
            throw new ParseJsonException("failed to convert initiative to json");
        }
    }

    public static Initiative convertJson(String jsonToTransform) throws ParseJsonException {
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);
            final String _id = jsonObject.getString("_id");
            final String description = jsonObject.getString("description");
            final String image = jsonObject.getString("image");
            final String nickname = jsonObject.getString("nickname");
            final String date = jsonObject.getString("date");
            final boolean isFromCurrentUser;
            if (jsonObject.has("is_from_current_user")) {
                isFromCurrentUser = jsonObject.getBoolean("is_from_current_user");
            } else {
                isFromCurrentUser = false;
            }
            return new Initiative(_id, description, image, nickname, date, isFromCurrentUser);
        } catch (Exception e) {
            throw new ParseJsonException("failed to convert jsno to initiative");
        }
    }

    public static List<Initiative> convertJsonList(String jsonToTransform) throws ParseJsonException {
        try {
            List<Initiative> initiatives = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonToTransform);
            for (int i = 0; i < jsonArray.length(); i ++) {
                initiatives.add(convertJson(jsonArray.getString(i)));
            }
            return initiatives;
        } catch (Exception e) {
            throw new ParseJsonException("failed to convert  the list of initiative to json");
        }
    }



}
