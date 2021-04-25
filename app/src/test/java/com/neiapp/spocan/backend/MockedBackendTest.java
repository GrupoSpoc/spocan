package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

@RunWith(RobolectricTestRunner.class)
public class MockedBackendTest {
    @Test
    public void initiativeCreationTest (){
        MockedBackend fakeBackend = new MockedBackend();
        ArrayList<Initiative> result = new ArrayList<>();
        String sizeResultString;
        Initiative  initiative = new Initiative("title", "description","image", false);
        CallbackVoid callback = new CallbackVoid() {
            @Override
            public void onSuccess() {}
        } ;

        fakeBackend.createInitiative(initiative,callback);
        result = fakeBackend.getAll();
        sizeResultString = Integer.toString(result.size());

        assertEquals(result.get(0),initiative);
        assertEquals(initiative.getId(),sizeResultString);
    }


}
