package com.neiapp.spocan.models;

import com.google.gson.JsonObject;
import com.neiapp.spocan.backend.ParseJsonException;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;


@RunWith(RobolectricTestRunner.class)
public class UserTest {

    public static final String NICKNAME = "nickname";
    public static final UserType TYPE = UserType.PERSON;
    public static final int AMOUNT_INITIATIVES = 10;
    public static final boolean IS_NOT_ADMIN = false;

    @Test
    public void testConstructor() {
        User user = new User(NICKNAME, TYPE);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertFalse(user.isAdmin());
        assertEquals(0, user.getAmountOfInitiatives());
    }
    @Test
    public void testConstructorWithWrongAmountInititatives() {
        User user = new User(NICKNAME, TYPE, AMOUNT_INITIATIVES, IS_NOT_ADMIN);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertFalse(IS_NOT_ADMIN);
        assertEquals(AMOUNT_INITIATIVES,user.getAmountOfInitiatives());

    }
    @Test
    public void testToJson() throws Exception {
        User user = new User(NICKNAME, TYPE);
        String json = user.toJson();

        JSONObject jsonObject = new JSONObject(json);

        assertEquals(NICKNAME, jsonObject.get("nickname").toString());
        assertEquals(TYPE.getId(), jsonObject.getInt("type_id"));
    }

    @Test
    public void testConvertJson() throws ParseJsonException {
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty("nickname", NICKNAME);
        jsonUser.addProperty("type_id", TYPE.getId());
        jsonUser.addProperty("amount_of_initiatives",AMOUNT_INITIATIVES);
        jsonUser.addProperty("admin", IS_NOT_ADMIN);


        User result = User.convertJson(jsonUser.toString());

        assertEquals(NICKNAME, result.getNickname());
        assertEquals(TYPE, result.getType());
        assertEquals(AMOUNT_INITIATIVES,result.getAmountOfInitiatives());
        assertFalse(result.isAdmin());


    }

    @Test
    public void convertJsonThrowException(){
        JsonObject wrong_jsonUser = new JsonObject();

      assertThrows(ParseJsonException.class,()-> User.convertJson(wrong_jsonUser.toString()));
    }
}