package com.neiapp.spocan.backend.rest;

public enum HTTPCodes {
    NOT_ACCEPTABLE(406),
    BAD_REQUEST_ERROR(400),
    SERVER_ERROR(500);

    private final int code;

    HTTPCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
