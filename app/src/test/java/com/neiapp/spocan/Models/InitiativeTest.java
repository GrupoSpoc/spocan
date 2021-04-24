 package com.neiapp.spocan.Models;

import com.google.gson.JsonObject;

import org.junit.Assert;
import org.junit.Test;

public class InitiativeTest {
    @Test
    public void testInitiativeCreation(){
        String  title = "title";
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = false;

        Initiative initiative = new Initiative(title, description, imageBase64, isFromCurrentUser);

        Assert.assertEquals(title, initiative.getTitle());
        Assert.assertEquals(description, initiative.getDescription());
        Assert.assertEquals(imageBase64, initiative.getImageBase64());
        Assert.assertFalse(initiative.isFromCurrentUser());
        Assert.assertNull(initiative.getId());
        Assert.assertNull(initiative.getNickName());

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

        Assert.assertEquals(title, initiative.getTitle());
        Assert.assertEquals(description, initiative.getDescription());
        Assert.assertEquals(imageBase64, initiative.getImageBase64());
        Assert.assertTrue(initiative.isFromCurrentUser());
        Assert.assertEquals(id, initiative.getId());
        Assert.assertEquals(nickname, initiative.getNickName());

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

        Assert.assertEquals(result.getDescription(), jsonInitiative.get("description").getAsString());
        Assert.assertEquals(result.getId(), jsonInitiative.get("id").getAsString());
        Assert.assertEquals(result.getImageBase64(), jsonInitiative.get("imageBase64").getAsString());
        Assert.assertEquals(result.getNickName(), jsonInitiative.get("nickName").getAsString());
        Assert.assertEquals(result.getTitle(), jsonInitiative.get("title").getAsString());
        Assert.assertFalse(result.isFromCurrentUser());


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

        Assert.assertEquals(title, result.getTitle());
        Assert.assertEquals(description, result.getDescription());
        Assert.assertEquals(imageBase64, result.getImageBase64());
        Assert.assertTrue(result.isFromCurrentUser());
        Assert.assertEquals(id, result.getId());
        Assert.assertEquals(nickname, result.getNickName());
    }

}