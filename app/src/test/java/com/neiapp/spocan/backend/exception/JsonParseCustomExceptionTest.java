package com.neiapp.spocan.backend.exception;

import junit.framework.TestCase;
import com.google.gson.JsonObject;
import com.neiapp.spocan.Models.Initiative;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertThrows;

@RunWith(RobolectricTestRunner.class)
public class JsonParseCustomExceptionTest extends TestCase {
 @Test
    public void jsonParseThrowException() {
     JsonObject wrongJson = new JsonObject();
     wrongJson.addProperty("friendly_message", "go to sleep");

      assertThrows(JsonParseCustomException.class, ()-> Initiative.convertJson(wrongJson.toString()));
 }
}