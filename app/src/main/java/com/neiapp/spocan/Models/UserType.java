package com.neiapp.spocan.Models;

import java.util.Arrays;

public enum UserType {
    PERSON(1),
    COMPANY(2);

    private final int id;

    UserType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static UserType fromIdOrElseThrow(int id) {
        return Arrays.stream(values())
                .filter(v -> v.id == id)
                .findFirst()
                .orElseThrow(() -> new UserTypeNotFoundException(id));
    }

    public static class UserTypeNotFoundException extends RuntimeException {
        private UserTypeNotFoundException(int id) {
            super(String.format("No UserType with id [%s] present", id));
        }
    }
}
