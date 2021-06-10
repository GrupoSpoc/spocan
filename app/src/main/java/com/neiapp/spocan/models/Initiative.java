package com.neiapp.spocan.models;

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
    private InitiativeStatus status;

    public Initiative(String _id, InitiativeStatus status, String description, String image, String nickname, String dateStrUTC) {
        this._id = _id;
        this.status = status;
        this.description = description;
        this.image = image;
        this.nickname = nickname;
        setDate(dateStrUTC);
    }

    public Initiative(String description, String image) {
        this.description = description;
        this.status = null;
        this.image = image;
        this._id = null;
        this.nickname = null;
        initDate();
    }

    public Initiative(String description, Bitmap bitmap) {
        this.description = description;
        this.status = null;
        setImage(bitmap);
        this._id = null;
        this.nickname = null;
        initDate();
    }

    public String getId() {
        return _id;
    }

    public InitiativeStatus getStatus(){return status;}

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
            String message = "failed to convert initiative to json: " + e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);

        }
    }

    public static Initiative convertJson(String jsonToTransform) throws ParseJsonException {
        try {
            JSONObject jsonObject = new JSONObject(jsonToTransform);


            final InitiativeStatus status = InitiativeStatus.fromIdOrElseThrow(Integer.parseInt(jsonObject.getString("status_id")));
            final String _id = jsonObject.getString("_id");
            final String description = jsonObject.getString("description");
            final String image = jsonObject.getString("image");
            final String nickname = jsonObject.getString("nickname");
            final String date = jsonObject.getString("date");

            return new Initiative(_id, status, description, image, nickname, date);
        } catch (Exception e) {
            String message = "failed to convert jsno to initiative: " + e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
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
            String message = "failed to convert  the list of initiative to json"+ e.getMessage();
            System.out.println(message);
            throw new ParseJsonException(message);
        }
    }



}
