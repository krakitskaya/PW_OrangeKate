package com.kate.project.api;

import io.restassured.specification.RequestSpecification;
import lombok.Builder;

@Builder
public class QueryParams {
    private Integer limit;
    private Integer offset;
    private String sortOrder;
    private String sortField;

    public RequestSpecification applyTo(RequestSpecification spec) {
        spec = ApiRequestBuilder.withQueryParam(spec, "limit", limit);
        spec = ApiRequestBuilder.withQueryParam(spec, "offset", offset);
        spec = ApiRequestBuilder.withQueryParam(spec, "sortOrder", sortOrder);
        spec = ApiRequestBuilder.withQueryParam(spec, "sortField", sortField);
        return spec;
    }
}