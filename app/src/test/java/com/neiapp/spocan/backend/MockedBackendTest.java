package com.neiapp.spocan.backend;

import com.neiapp.spocan.backend.callback.CallbackInstance;
import com.neiapp.spocan.backend.callback.CallbackVoid;
import com.neiapp.spocan.models.Initiative;
import com.neiapp.spocan.models.InitiativeBatch;
import com.neiapp.spocan.models.UserType;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        Initiative initiative = new Initiative("description", "image");
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

        Initiative initiative_first = new Initiative("description", "image");
        Initiative initiative_second = new Initiative("description", "image");
        CallbackVoid call = new CallbackVoid() {
            @Override
            public void onSuccess() {
            }
        };

        fakeBackend.createInitiative(initiative_first, call);
        fakeBackend.createInitiative(initiative_second, call);
        fakeBackend.getAllInitiatives(null, false, new CallbackInstance<InitiativeBatch>() {
            @Override
            public void onSuccess(InitiativeBatch batch) {
                assertEquals(originalSize + 2, MockedBackend.initiative_store.size());
                // tomo la penúltima creada, debería ser initiative_first
                assertEquals(String.valueOf(originalSize + 1), batch.getInitiatives().get(originalSize).getId());
                // tomo la última creada, debería ser initiative_second
                assertEquals(String.valueOf(originalSize + 2), batch.getInitiatives().get(originalSize + 1).getId());
                assertFalse(batch.isLastBatch());
            }
        });
    }

    @Test
    public void doAuthenticate() {
        MockedBackend.doAuthenticate(user -> {
            assertEquals("TestUser", user.getNickname());
            assertEquals(UserType.PERSON, user.getType());
        });
    }
}
