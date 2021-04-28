package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    public static final String NICKNAME = "nickname";
    public static final UserType TYPE = UserType.PERSON;

    @Test
    public void testConstructor() {
        User user = new User(NICKNAME, TYPE);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
    }

    @Test
    public void testToJson() throws JSONException {
        User user = new User(NICKNAME, TYPE);
        String json = user.toJson();

        JSONObject jsonObject = new JSONObject(json);
        assertEquals(NICKNAME, jsonObject.get("nickname").toString());
        assertEquals(TYPE.getId(), jsonObject.getInt("type"));
    }

    @Test
    public void testConvertJson() {
        JsonObject jsonUser = new JsonObject();

        jsonUser.addProperty("nickname", NICKNAME);
        jsonUser.addProperty("type", TYPE.getId());

        User result = User.convertJson(jsonUser.toString());

        assertEquals(NICKNAME, result.getNickname());
        assertEquals(TYPE, result.getType());
    }

    @Test
    public void testToJsonAndThenConvertJson() {
        User user = new User(NICKNAME, TYPE);
        String jsonUser = user.toJson();

        User result = User.convertJson(jsonUser);

        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(user.getType(), result.getType());
    }
}