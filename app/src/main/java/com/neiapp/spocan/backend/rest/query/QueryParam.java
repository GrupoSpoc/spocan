package com.neiapp.spocan.backend.rest.query;

public enum QueryParam {
    ORDER("order");

    private final String label;

    QueryParam(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
