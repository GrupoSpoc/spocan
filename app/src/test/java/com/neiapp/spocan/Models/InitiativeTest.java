 package com.neiapp.spocan.Models;

 import android.graphics.Bitmap;
 import com.google.gson.JsonObject;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.robolectric.RobolectricTestRunner;

 import java.time.LocalDateTime;
 import java.time.Month;
 import java.time.ZoneOffset;

 import static org.junit.Assert.assertEquals;
 import static org.junit.Assert.assertFalse;
 import static org.junit.Assert.assertNotNull;
 import static org.junit.Assert.assertNull;
 import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class InitiativeTest {
    @Test
    public void testInitiativeCreation() {
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = false;

        Initiative initiative = new Initiative(description, imageBase64, isFromCurrentUser);
        long testNowMillis = LocalDateTime.now(Initiative.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertFalse(initiative.isFromCurrentUser());
        assertNull(initiative.getId());
        assertNull(initiative.getNickname());

        // para asegurarme que la iniciativa se creó con el now, comparo los ms del now antes
        // de la creación y después, con una tolerancia de 5000 ms.
        long initiativeNowMillis = initiative.getDateUTC().toInstant(ZoneOffset.UTC).toEpochMilli();
        assertTrue(initiativeNowMillis - testNowMillis <= 5000);
    }

    @Test
    public void testInitiativeCreationWithout_IdNorNickname(){
        String  description = "description";
        String  imageBase64 = "imageBase64";
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname, dateStrUTC, isFromCurrentUser);

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertTrue(initiative.isFromCurrentUser());
        assertEquals(_id, initiative.getId());
        assertEquals(nickname, initiative.getNickname());
        assertEquals(date, initiative.getDateUTC());
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
        assertEquals(result.getId(), jsonInitiative.get("_id").getAsString());
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
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname, dateStrUTC, isFromCurrentUser);

        String jsonInitiative = initiative.toJson();
        Initiative result = Initiative.convertJson(jsonInitiative);

        assertEquals(description, result.getDescription());
        assertEquals(imageBase64, result.getImageBase64());
        assertTrue(result.isFromCurrentUser());
        assertEquals(_id, result.getId());
        assertEquals(nickname, result.getNickname());
        assertEquals(date, result.getDateUTC());
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
        assertNull(initiative.getId());
        assertNull(initiative.getNickname());
    }

    @Test
    public void testSettingAndGettingImage() {
        String  description = "description";
        String  imageBase64 = null;
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname, dateStrUTC, isFromCurrentUser);

        initiative.setImage(bitmap);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
    }

    @Test
    public void testDateZones() {
        String  description = "description";
        String  imageBase64 = null;
        boolean isFromCurrentUser = true;
        String nickname = "lolo";
        String _id = "_id";
        LocalDateTime dateUTC = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = dateUTC.toString();
        LocalDateTime zonedBsAsDate = dateUTC.minusHours(Initiative.ZONE_BS_AS_HOURS);

        Initiative initiative = new Initiative(_id, description, imageBase64, nickname, dateStrUTC, isFromCurrentUser);

        assertEquals(dateUTC, initiative.getDateUTC());
        assertEquals(zonedBsAsDate, initiative.getDateLocal());
    }
}