 package com.neiapp.spocan.Models;

 import android.graphics.Bitmap;

 import com.google.gson.JsonObject;

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
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = false;

        Initiative initiative = new Initiative(description, imageBase64, isFromCurrentUser);

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertFalse(initiative.isFromCurrentUser());
        assertNull(initiative.get_id());
        assertNull(initiative.getNickname());
    }

    @Test
    public void testInitiativeCreationWithout(){
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname,isFromCurrentUser);

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertTrue(initiative.isFromCurrentUser());
        assertEquals(_id, initiative.get_id());
        assertEquals(nickname, initiative.getNickname());
    }

    @Test
    public void testInitiativeTransformJSON()  {
        JsonObject jsonInitiative = new JsonObject();

        jsonInitiative.addProperty("_id", "_id");
        jsonInitiative.addProperty("description", "description");
        jsonInitiative.addProperty("image", "imageBase64");
        jsonInitiative.addProperty("nickname", "nickname");
        jsonInitiative.addProperty("isFromCurrentUser", "false");

        Initiative result = Initiative.convertJson(jsonInitiative.toString());

        assertEquals(result.getDescription(), jsonInitiative.get("description").getAsString());
        assertEquals(result.get_id(), jsonInitiative.get("_id").getAsString());
        assertEquals(result.getImageBase64(), jsonInitiative.get("image").getAsString());
        assertEquals(result.getNickname(), jsonInitiative.get("nickname").getAsString());
        assertFalse(result.isFromCurrentUser());
    }

    @Test
    public void  toJsonAndThemFromJson(){
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname,isFromCurrentUser);

        String jsonInitiative = initiative.toJson();
        Initiative result = Initiative.convertJson(jsonInitiative);

        assertEquals(description, result.getDescription());
        assertEquals(imageBase64, result.getImageBase64());
        assertTrue(result.isFromCurrentUser());
        assertEquals(_id, result.get_id());
        assertEquals(nickname, result.getNickname());
    }

    @Test
    public void testBuildInitiativeWithImage() {
        String  description = "description";
        boolean isFromCurrentUser = true;
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(description, bitmap, isFromCurrentUser);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
        assertEquals(description, initiative.getDescription());
        assertTrue(initiative.isFromCurrentUser());
        assertNull(initiative.get_id());
        assertNull(initiative.getNickname());
    }

    @Test
    public void testSettingAndGettingImage() {
        String  description = "description";
        String  imageBase64 = null;
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname,isFromCurrentUser);

        initiative.setImage(bitmap);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
    }
}