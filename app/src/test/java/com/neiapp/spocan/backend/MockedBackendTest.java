package com.neiapp.spocan.backend;

import com.neiapp.spocan.Models.Initiative;
import com.neiapp.spocan.Models.UserType;
import com.neiapp.spocan.backend.callback.CallbackCollection;
import com.neiapp.spocan.backend.callback.CallbackVoid;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MockedBackendTest {
    @After
    public void resetStorage(){
        MockedBackend.resetStorage();
    }

    @Test
    public void initiativeCreationTest() {
        MockedBackend fakeBackend = new MockedBackend();
        int originalSize = MockedBackend.initiative_store.size();
        Initiative created;
        Initiative initiative = new Initiative("description", "image", false);
        CallbackVoid callback = new CallbackVoid() {
            @Override
            public void onSuccess() {
            }
        };
        fakeBackend.createInitiative(initiative, callback);
        assertEquals(originalSize + 1, MockedBackend.initiative_store.size());
        created = MockedBackend.initiative_store.get(originalSize);
        assertEquals(String.valueOf(originalSize + 1), created.getId());
    }

    @Test
    public void getAllInitiativeTest() {
        MockedBackend fakeBackend = new MockedBackend();
        int originalSize = MockedBackend.initiative_store.size();

        Initiative initiative_first = new Initiative("description", "image", false);
        Initiative initiative_second = new Initiative("description", "image", false);
        CallbackVoid call = new CallbackVoid() {
            @Override
            public void onSuccess() {
            }
        };

        fakeBackend.createInitiative(initiative_first, call);
        fakeBackend.createInitiative(initiative_second, call);
        fakeBackend.getAllInitiatives(new CallbackCollection<Initiative>() {
            @Override
            public void onSuccess(List<Initiative> collection) {
                assertEquals(originalSize + 2, MockedBackend.initiative_store.size());
                // tomo la penúltima creada, debería ser initiative_first
                assertEquals(String.valueOf(originalSize + 1), collection.get(originalSize).getId());
                // tomo la última creada, debería ser initiative_second
                assertEquals(String.valueOf(originalSize + 2), collection.get(originalSize + 1).getId());
            }
        });
    }

    @Test
    public void doAuthenticate() {
        MockedBackend.doAuthenticate(user -> {
            assertEquals("CurrentUser", user.getNickname());
            assertEquals(UserType.PERSON, user.getType());
        });
    }
}
