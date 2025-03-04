package com.nagp.products.entity;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.List;
import java.util.Map;

public class PaginatedResult<T> {

    private List<T> data;
    private Map<String, AttributeValue> nextPageKey;

    public PaginatedResult(List<T> data, Map<String, AttributeValue> nextPageKey) {
        this.data = data;
        this.nextPageKey = nextPageKey;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Map<String, AttributeValue> getNextPageKey() {
        return nextPageKey;
    }

    public void setNextPageKey(Map<String, AttributeValue> nextPageKey) {
        this.nextPageKey = nextPageKey;
    }
}

