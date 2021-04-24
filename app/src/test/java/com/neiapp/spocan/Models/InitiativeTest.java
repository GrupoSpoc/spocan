 package com.neiapp.spocan.Models;

import android.graphics.Bitmap;

import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class InitiativeTest {
    @Test
    public void testInitiativeCreation(){
        String  title = "title";
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = false;

        Initiative initiative = new Initiative(title, description, imageBase64, isFromCurrentUser);

        assertEquals(title, initiative.getTitle());
        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertFalse(initiative.isFromCurrentUser());
        assertNull(initiative.getId());
        assertNull(initiative.getNickName());
    }

    @Test
    public void testInitiativeCreationWithout(){
        String  title = "title";
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String id= "id";

        Initiative initiative = new Initiative(id,title, description, imageBase64, nickname,isFromCurrentUser);

        assertEquals(title, initiative.getTitle());
        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertTrue(initiative.isFromCurrentUser());
        assertEquals(id, initiative.getId());
        assertEquals(nickname, initiative.getNickName());
    }

    @Test
    public void testInitiativeTransformJSON()  {

        JsonObject jsonInitiative = new JsonObject();

        jsonInitiative.addProperty("id", "id");
        jsonInitiative.addProperty("title", "title");
        jsonInitiative.addProperty("description", "description");
        jsonInitiative.addProperty("imageBase64", "imageBase64");
        jsonInitiative.addProperty("nickName", "nickname");
        jsonInitiative.addProperty("isFromCurrentUser", "false");

        Initiative result = Initiative.convertJson(jsonInitiative.toString());

        assertEquals(result.getDescription(), jsonInitiative.get("description").getAsString());
        assertEquals(result.getId(), jsonInitiative.get("id").getAsString());
        assertEquals(result.getImageBase64(), jsonInitiative.get("imageBase64").getAsString());
        assertEquals(result.getNickName(), jsonInitiative.get("nickName").getAsString());
        assertEquals(result.getTitle(), jsonInitiative.get("title").getAsString());
        assertFalse(result.isFromCurrentUser());
    }

    @Test
    public void  toJsonAndThemFromJson(){
        String  title = "title";
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String id= "id";

        Initiative initiative = new Initiative(id,title, description, imageBase64, nickname,isFromCurrentUser);

        String jsonInitiative = initiative.toJson();
        Initiative result = Initiative.convertJson(jsonInitiative);

        assertEquals(title, result.getTitle());
        assertEquals(description, result.getDescription());
        assertEquals(imageBase64, result.getImageBase64());
        assertTrue(result.isFromCurrentUser());
        assertEquals(id, result.getId());
        assertEquals(nickname, result.getNickName());
    }

    @Test
    public void testBuildInitiativeWithImage() {
        String  title = "title";
        String  description = "description";
        boolean isFromCurrentUser = true;
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(title, description, bitmap, isFromCurrentUser);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
        assertEquals(title, initiative.getTitle());
        assertEquals(description, initiative.getDescription());
        assertTrue(initiative.isFromCurrentUser());
        assertNull(initiative.getId());
        assertNull(initiative.getNickName());
    }

    @Test
    public void testSettingAndGettingImage() {
        String  title = "title";
        String  description = "description";
        String  imageBase64 = null;
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String id= "id";
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(id,title, description, imageBase64, nickname,isFromCurrentUser);

        initiative.setImage(bitmap);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
    }
}