package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    public static final String NICKNAME = "nickname";
    public static final UserType TYPE = UserType.PERSON;
    public static final int AMOUNT_INITIATIVES = 10;
    public static final boolean IS_NOT_ADMIN = false;
    public static final boolean IS_ADMIN = true;


    @Test
    public void testConstructor() {
        User user = new User(NICKNAME, TYPE);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertFalse(user.isAdmin());
        assertEquals(0, user.getAmountOfInititatives());

    }
    @Test
    public void testConstructorWithWrongAmountInititatives() {
        User user = new User(NICKNAME, TYPE, -4, IS_NOT_ADMIN);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertFalse(IS_NOT_ADMIN);
        assertEquals(0,user.getAmountOfInititatives());

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
        jsonUser.addProperty("amountOfInitiatives",AMOUNT_INITIATIVES);
        jsonUser.addProperty("admin", IS_NOT_ADMIN);


        User result = User.convertJson(jsonUser.toString());

        assertEquals(NICKNAME, result.getNickname());
        assertEquals(TYPE, result.getType());
        assertEquals(AMOUNT_INITIATIVES,result.getAmountOfInititatives());
        assertFalse(result.isAdmin());
    }

    @Test
    public void testToJsonAndThenConvertJsonIfUserIsNotAdmin()  {
        User user = new User(NICKNAME, TYPE,AMOUNT_INITIATIVES,IS_NOT_ADMIN);
        String jsonUser = user.toJson();

        User result = User.convertJson(jsonUser);

        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(user.getType(), result.getType());
        assertFalse(result.isAdmin());
        assertEquals(user.getAmountOfInititatives(),result.getAmountOfInititatives());
    }

    @Test
    public void testToJsonAndThenConvertJsonIfUserIsAdmin()  {
        User user = new User(NICKNAME, TYPE,AMOUNT_INITIATIVES,IS_ADMIN);
        String jsonUser = user.toJson();

        User result = User.convertJson(jsonUser);

        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(user.getType(), result.getType());
        assertTrue(result.isAdmin());
        assertEquals(user.getAmountOfInititatives(),result.getAmountOfInititatives());
    }
}