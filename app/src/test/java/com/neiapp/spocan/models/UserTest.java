package com.neiapp.spocan.models;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.neiapp.spocan.backend.ParseJsonException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;


@RunWith(RobolectricTestRunner.class)
public class UserTest {

    public static final String NICKNAME = "nickname";
    public static final UserType TYPE = UserType.PERSON;
    public static final int AMOUNT_INITIATIVES = 10;
    public static final boolean IS_NOT_ADMIN = false;
    public static final Map<Integer,Integer> INITIATIVE_MAP = new HashMap<>();

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
        User user = new User(NICKNAME, TYPE, AMOUNT_INITIATIVES, IS_NOT_ADMIN,INITIATIVE_MAP);

        assertEquals(NICKNAME, user.getNickname());
        assertEquals(TYPE, user.getType());
        assertFalse(IS_NOT_ADMIN);
        assertEquals(AMOUNT_INITIATIVES,user.getAmountOfInitiatives());
        assertEquals(INITIATIVE_MAP,user.getInitiativesByStatus());

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
        Gson gson = new Gson();
        populateMap();
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty("nickname", NICKNAME);
        jsonUser.addProperty("type_id", TYPE.getId());
        jsonUser.addProperty("amount_of_initiatives",AMOUNT_INITIATIVES);
        jsonUser.addProperty("admin", IS_NOT_ADMIN);
        jsonUser.add("initiatives_by_status", gson.toJsonTree(INITIATIVE_MAP));

        User result = User.convertJson(jsonUser.toString());

        assertEquals(NICKNAME, result.getNickname());
        assertEquals(TYPE, result.getType());
        assertEquals(AMOUNT_INITIATIVES,result.getAmountOfInitiatives());
        assertEquals(INITIATIVE_MAP, result.getInitiativesByStatus());
        assertFalse(result.isAdmin());

    }

    private void populateMap() {
        INITIATIVE_MAP.put(InitiativeStatus.PENDING.getId(),3);
        INITIATIVE_MAP.put(InitiativeStatus.APPROVED.getId(),1);
        INITIATIVE_MAP.put(InitiativeStatus.REJECTED.getId(),1);
    }

    @Test
    public void convertJsonThrowException(){
        JsonObject wrong_jsonUser = new JsonObject();

      assertThrows(ParseJsonException.class,()-> User.convertJson(wrong_jsonUser.toString()));
    }
}