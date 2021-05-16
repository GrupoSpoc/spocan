package com.neiapp.spocan.backend.rest.query;

import java.util.HashMap;
import java.util.Map;

public class QueryParamsBuilder {
    final Map<String, String> queryParams;

    public QueryParamsBuilder() {
        this.queryParams = new HashMap<>();
    }

    public QueryParamsBuilder withParam(QueryParam queryParam, String value) {
        this.queryParams.put(queryParam.getLabel(), value);
        return this;
    }

    public Map<String, String> build() {
        return queryParams;
    }
}
