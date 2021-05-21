package com.neiapp.spocan.backend.rest;

public enum HTTPCodes {
    NOT_ACCEPTABLE(406),
    SERVER_ERROR(500),
    ERROR_PARSE_JSON(409),
    BAD_REQUEST_DEFAULT(400),
    // server bad requests
    BAD_REQUEST(800),
    NICKNAME_ALREADY_TAKEN(801);


    private final int code;

    HTTPCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
