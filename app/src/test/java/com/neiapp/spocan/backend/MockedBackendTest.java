package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MockedBackendTest {
    @Test
    public void initiativeCreationTest (){
        MockedBackend fakeBackend = new MockedBackend();
        Initiative first ;
        Initiative  initiative = new Initiative("description","image", false);
        CallbackVoid callback = new CallbackVoid() {
            @Override
            public void onSuccess() {}
        } ;
        fakeBackend.createInitiative(initiative,callback);
        assertEquals(1,MockedBackend.initiative_store.size());
        first = MockedBackend.initiative_store.get(0);
        assertEquals("1", first.getId());
    }
}
