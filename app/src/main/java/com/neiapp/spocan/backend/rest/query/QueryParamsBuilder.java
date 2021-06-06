package com.neiapp.spocan.backend.rest.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryParamsBuilder {
    final Map<String, List<String>> queryParams;

    public QueryParamsBuilder() {
        this.queryParams = new HashMap<>();
    }

    public QueryParamsBuilder withParam(QueryParam queryParam, String value) {
        this.queryParams.computeIfAbsent(queryParam.getLabel(), k -> new ArrayList<>()).add(value);
        return this;
    }

    public Map<String, List<String>> build() {
        return queryParams;
    }
}
