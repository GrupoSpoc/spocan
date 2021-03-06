package com.neiapp.spocan.backend.rest.query;

public enum QueryParam {
    ORDER("order"),
    STATUS("statusId"),
    CURRENT_USER("currentUser"),
    DATE_TO("dateTo"),
    LIMIT("limit");

    private final String label;

    QueryParam(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
