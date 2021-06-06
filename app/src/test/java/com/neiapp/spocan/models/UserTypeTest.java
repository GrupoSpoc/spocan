package com.neiapp.spocan.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(RobolectricTestRunner.class)
public class UserTypeTest {

    @Test
    public void testGetId() {
        assertEquals(1, UserType.PERSON.getId());
        assertEquals(2, UserType.COMPANY.getId());

    }

    @Test
    public void testFromIdHappyCases() {
        assertEquals(UserType.PERSON, UserType.fromIdOrElseThrow(1));
        assertEquals(UserType.COMPANY, UserType.fromIdOrElseThrow(2));
    }

    @Test
    public void testFromIdThrowsUserTypeNotFoundException() {
        final int invalidId = UserType.values().length + 1;
        final String expectedMessage = String.format("No UserType with id [%s] present", invalidId);

        UserType.UserTypeNotFoundException exception = assertThrows(UserType.UserTypeNotFoundException.class,
                () -> UserType.fromIdOrElseThrow(invalidId));
        assertEquals(expectedMessage, exception.getMessage());
    }
}