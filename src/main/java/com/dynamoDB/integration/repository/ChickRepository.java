package com.dynamoDB.integration.repository;

import com.dynamoDB.integration.config.DynamoDBConfig;
import com.dynamoDB.integration.model.Chick;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChickRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String tablename="HotChicks";


    public ChickRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveChick(Chick chick){
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Name", AttributeValue.builder().s(chick.getName()).build());
        item.put("Score", AttributeValue.builder().n(String.valueOf(chick.getScore())).build());
        item.put("Age", AttributeValue.builder().n(String.valueOf(chick.getAge())).build());
        item.put("Relation", AttributeValue.builder().s(chick.getRelation()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tablename)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    public Chick getOneChick(String name){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Name", AttributeValue.builder().s(name).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(tablename)
                .key(key)
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();
        if (item == null || item.isEmpty()) return null;

        Chick chick = new Chick();
        chick.setName(item.get("Name").s());
        chick.setScore(Integer.parseInt(item.get("Score").n()));
        chick.setAge(Integer.parseInt(item.get("Age").n()));
        chick.setRelation(item.get("Relation").s());

        return chick;
    }

    public List<Chick> getAllChicks(){
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tablename)
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Chick> chicks = new ArrayList<>();

        for (Map<String, AttributeValue> item : scanResponse.items()) {
            Chick chick = new Chick();
            chick.setName(item.get("Name").s());
            chick.setScore(Integer.parseInt(item.get("Score").n()));
            chick.setAge(Integer.parseInt(item.get("Age").n()));
            chick.setRelation(item.get("Relation").s());
            chicks.add(chick);
        }

        return chicks;
    }


}
