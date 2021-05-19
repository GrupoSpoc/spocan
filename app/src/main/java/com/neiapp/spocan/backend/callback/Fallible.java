package com.neiapp.spocan.backend.callback;

public interface Fallible {
    default void onFailure(String message, int httpStatus) {
        System.out.println(String.format("An error has occurred. Message: %s - Status: %s", message, httpStatus));
    }
}
