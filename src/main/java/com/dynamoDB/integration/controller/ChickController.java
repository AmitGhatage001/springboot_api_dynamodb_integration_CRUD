package com.dynamoDB.integration.controller;

import com.dynamoDB.integration.model.Chick;
import com.dynamoDB.integration.repository.ChickRepository;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ChickController {

    private final ChickRepository chickRepository;


    public ChickController(ChickRepository chickRepository) {
        this.chickRepository = chickRepository;
    }

    @PostMapping
    public String addChick(@RequestBody Chick chick){
        chickRepository.saveChick(chick);
        return "Chick Saved Successfully";
    }

    @GetMapping({"{name}"})
    public Chick getChick(@PathVariable String name){
        return chickRepository.getOneChick(name);
    }

    @GetMapping("/all")
    public List<Chick> getAllChicks(){
        return chickRepository.getAllChicks();
    }
}
