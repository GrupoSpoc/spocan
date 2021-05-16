package com.neiapp.spocan.backend.rest.query;

import java.util.HashMap;
import java.util.Map;

public class QueryParamBuilder {
    final Map<String, String> queryParams;

    public QueryParamBuilder() {
        this.queryParams = new HashMap<>();
    }

    public QueryParamBuilder withParam(QueryParam queryParam, String value) {
        this.queryParams.put(queryParam.getLabel(), value);
        return this;
    }

    public Map<String, String> build() {
        return queryParams;
    }
}
