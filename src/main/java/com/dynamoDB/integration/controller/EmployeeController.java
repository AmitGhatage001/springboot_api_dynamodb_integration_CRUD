package com.dynamoDB.integration.controller;

import com.dynamoDB.integration.model.Employee;
import com.dynamoDB.integration.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;


    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public String addChick(@RequestBody Employee employee){
        employeeRepository.saveChick(employee);
        return "Employee Saved Successfully";
    }

    @GetMapping({"{name}"})
    public Employee getChick(@PathVariable String name){
        return employeeRepository.getOneChick(name);
    }

    @GetMapping("/all")
    public List<Employee> getAllChicks(){
        return employeeRepository.getAllChicks();
    }
}
