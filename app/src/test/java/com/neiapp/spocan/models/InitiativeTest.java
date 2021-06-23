 package com.neiapp.spocan.models;

 import static org.junit.Assert.assertEquals;
 import static org.junit.Assert.assertNotNull;
 import static org.junit.Assert.assertNull;
 import static org.junit.Assert.assertThrows;
 import static org.junit.Assert.assertTrue;

 import android.graphics.Bitmap;

 import com.google.gson.JsonObject;
 import com.neiapp.spocan.backend.ParseJsonException;

 import org.json.JSONException;
 import org.json.JSONObject;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.robolectric.RobolectricTestRunner;

 import java.time.LocalDateTime;
 import java.time.Month;
 import java.time.ZoneOffset;

@RunWith(RobolectricTestRunner.class)
public class InitiativeTest {
    @Test
    public void testInitiativeCreation() {
        String  description = "description";
        String  imageBase64 = "imageBase64";

        Initiative initiative = new Initiative(description, imageBase64);
        long testNowMillis = LocalDateTime.now(Initiative.UTC).toInstant(ZoneOffset.UTC).toEpochMilli();

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
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
        String nickname = "lolo";
        String _id = "_id";
        String reject_motive = "Rechazada por no ser iniciativa xd";
        InitiativeStatus status = InitiativeStatus.APPROVED;
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();

        Initiative initiative = new Initiative(_id, status, description, imageBase64, nickname, dateStrUTC, reject_motive);

        assertEquals(description, initiative.getDescription());
        assertEquals(imageBase64, initiative.getImageBase64());
        assertEquals(_id, initiative.getId());
        assertEquals(status, initiative.getStatus());
        assertEquals(nickname, initiative.getNickname());
        assertEquals(date, initiative.getDateUTC());
        assertEquals(reject_motive, initiative.getReject_motive());
    }

    @Test
    public void testToJson() throws JSONException, ParseJsonException {
        String  description = "description";
        String  imageBase64 = "imageBase64";

        Initiative initiative = new Initiative(description, imageBase64);

        String json = initiative.toJson();

        JSONObject jsonObject = new JSONObject(json);
        assertEquals(description, jsonObject.getString("description"));
        assertEquals(imageBase64, jsonObject.getString("image"));
        assertTrue(jsonObject.has("date"));

        String date = jsonObject.getString("date");
        assertNotNull(LocalDateTime.parse(date));
    }

    @Test
    public void testInitiativeTransformJSON() throws ParseJsonException {
        JsonObject jsonInitiative = new JsonObject();
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();

        jsonInitiative.addProperty("_id", "_id");
        jsonInitiative.addProperty("status_id", 1);
        jsonInitiative.addProperty("description", "description");
        jsonInitiative.addProperty("image", "imageBase64");
        jsonInitiative.addProperty("nickname", "nickname");
        jsonInitiative.addProperty("date", dateStrUTC);
        jsonInitiative.addProperty("reject_motive", "reject_motive");

        Initiative result = Initiative.convertJson(jsonInitiative.toString());

        assertEquals(result.getDescription(), jsonInitiative.get("description").getAsString());
        assertEquals(result.getId(), jsonInitiative.get("_id").getAsString());
        assertEquals(result.getStatus().getId(), jsonInitiative.get("status_id").getAsInt());
        assertEquals(result.getImageBase64(), jsonInitiative.get("image").getAsString());
        assertEquals(result.getNickname(), jsonInitiative.get("nickname").getAsString());
        assertEquals(result.getReject_motive(), jsonInitiative.get("reject_motive").getAsString());
    }

    @Test
    public void testInitiativeTransformJSONWithoutRejectMotive() throws ParseJsonException {
        JsonObject jsonInitiative = new JsonObject();
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();

        jsonInitiative.addProperty("_id", "_id");
        jsonInitiative.addProperty("status_id", 1);
        jsonInitiative.addProperty("description", "description");
        jsonInitiative.addProperty("image", "imageBase64");
        jsonInitiative.addProperty("nickname", "nickname");
        jsonInitiative.addProperty("date", dateStrUTC);

        Initiative result = Initiative.convertJson(jsonInitiative.toString());

        assertEquals(result.getDescription(), jsonInitiative.get("description").getAsString());
        assertEquals(result.getId(), jsonInitiative.get("_id").getAsString());
        assertEquals(result.getStatus().getId(), jsonInitiative.get("status_id").getAsInt());
        assertEquals(result.getImageBase64(), jsonInitiative.get("image").getAsString());
        assertEquals(result.getNickname(), jsonInitiative.get("nickname").getAsString());
        assertNull(result.getReject_motive());
    }

    @Test
    public void testBuildInitiativeWithImage() {
        String  description = "description";
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(description, bitmap);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
        assertEquals(description, initiative.getDescription());
        assertNull(initiative.getId());
        assertNull(initiative.getNickname());
    }

    @Test
    public void testSettingAndGettingImage() {
        String  description = "description";
        String  imageBase64 = null;
        String nickname = "lolo";
        String _id = "_id";
        InitiativeStatus status = InitiativeStatus.APPROVED;
        String reject_motive = "Rechazada por no ser una iniciativa xd";
        LocalDateTime date = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = date.toString();
        final Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ALPHA_8);

        Initiative initiative = new Initiative(_id, status,  description, imageBase64, nickname, dateStrUTC, reject_motive);

        initiative.setImage(bitmap);

        assertNotNull(initiative.getImageBase64());
        assertNotNull(initiative.getImage());
    }

    @Test
    public void testDateZones() {
        String  description = "description";
        String  imageBase64 = null;
        String nickname = "lolo";
        String _id = "_id";
        InitiativeStatus status = InitiativeStatus.APPROVED;
        String reject_motive = "Rechazada por no ser una iniciativa xd";
        LocalDateTime dateUTC = LocalDateTime.of(1980, Month.APRIL, 20, 10, 30);
        String dateStrUTC = dateUTC.toString();
        LocalDateTime zonedBsAsDate = dateUTC.minusHours(Initiative.ZONE_BS_AS_HOURS);

        Initiative initiative = new Initiative(_id, status, description, imageBase64, nickname, dateStrUTC, reject_motive);

        assertEquals(dateUTC, initiative.getDateUTC());
        assertEquals(zonedBsAsDate, initiative.getDateLocal());
    }

    @Test
    public void convertJsonThrowException(){
        JsonObject wrong_jsonInitiative = new JsonObject();

        assertThrows(ParseJsonException.class,()-> User.convertJson(wrong_jsonInitiative.toString()));
    }

}