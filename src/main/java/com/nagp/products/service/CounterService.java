package com.nagp.products.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CounterService {
    private final AmazonDynamoDB dynamoDBClient;
    private static final String COUNTER_TABLE_NAME = "CounterTable";

    public CounterService() {
        this.dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
    }

    public String getNextProductId() {
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("counterName", new AttributeValue().withS("productId"));

        Map<String, AttributeValueUpdate> updates = new HashMap<>();
        updates.put("lastId", new AttributeValueUpdate()
                .withAction(AttributeAction.ADD)
                .withValue(new AttributeValue().withN("1")));  // Increment by 1

        UpdateItemRequest updateItemRequest = new UpdateItemRequest()
                .withTableName(COUNTER_TABLE_NAME)
                .withKey(key)
                .withAttributeUpdates(updates)
                .withReturnValues(ReturnValue.UPDATED_NEW);

        UpdateItemResult result = dynamoDBClient.updateItem(updateItemRequest);
        return "PROD"+result.getAttributes().get("lastId").getN();
       // return Integer.parseInt(result.getAttributes().get("lastId").getN());
    }

    /*public static void main(String[] args) {
        CounterService counterService = new CounterService();
        int newProductId = counterService.getNextProductId();
        System.out.println("Next Product ID: " + newProductId);
    }*/
}

