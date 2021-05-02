package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    public static final String NICKNAME = "nickname";
    public static final UserType TYPE = UserType.PERSON;
    public static  int AMOUNT_INITIATIVES = 10;


    @Test
    public void testConstructor() {
        User user = new User(NICKNAME, TYPE);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
    }
    @Test
    public void testConstructorWithWrongAmountInititatives() {
        User user = new User(NICKNAME, TYPE, -4);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertEquals(0,user.getAmount_inititatives());

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
        jsonUser.addProperty("amount_initiatives",AMOUNT_INITIATIVES);
        User result = User.convertJson(jsonUser.toString());

        assertEquals(NICKNAME, result.getNickname());
        assertEquals(TYPE, result.getType());
        assertEquals(AMOUNT_INITIATIVES,result.getAmount_inititatives());
    }

    @Test
    public void testToJsonAndThenConvertJson()  {
        User user = new User(NICKNAME, TYPE,AMOUNT_INITIATIVES);
        String jsonUser = user.toJson();

        User result = User.convertJson(jsonUser);

        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(user.getType(), result.getType());
        assertEquals(user.getAmount_inititatives(),(int)result.getAmount_inititatives());
    }

}