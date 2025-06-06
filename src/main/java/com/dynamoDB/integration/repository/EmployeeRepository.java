package com.dynamoDB.integration.repository;

import com.dynamoDB.integration.model.Employee;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeRepository {
    private final DynamoDbClient dynamoDbClient;
    private final String tablename="Employees";


    public EmployeeRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveChick(Employee employee){
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Name", AttributeValue.builder().s(employee.getName()).build());
        item.put("Score", AttributeValue.builder().n(String.valueOf(employee.getSalary())).build());
        item.put("Age", AttributeValue.builder().n(String.valueOf(employee.getAge())).build());
        item.put("Relation", AttributeValue.builder().s(employee.getPerformance()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tablename)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }

    public Employee getOneChick(String name){
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("Name", AttributeValue.builder().s(name).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(tablename)
                .key(key)
                .build();

        Map<String, AttributeValue> item = dynamoDbClient.getItem(request).item();
        if (item == null || item.isEmpty()) return null;

        Employee employee = new Employee();
        employee.setName(item.get("Name").s());
        employee.setSalary(Integer.parseInt(item.get("Score").n()));
        employee.setAge(Integer.parseInt(item.get("Age").n()));
        employee.setPerformance(item.get("Relation").s());

        return employee;
    }

    public List<Employee> getAllChicks(){
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tablename)
                .build();

        ScanResponse scanResponse = dynamoDbClient.scan(scanRequest);
        List<Employee> employees = new ArrayList<>();

        for (Map<String, AttributeValue> item : scanResponse.items()) {
            Employee employee = new Employee();
            employee.setName(item.get("Name").s());
            employee.setSalary(Integer.parseInt(item.get("Score").n()));
            employee.setAge(Integer.parseInt(item.get("Age").n()));
            employee.setPerformance(item.get("Relation").s());
            employees.add(employee);
        }

        return employees;
    }


}
