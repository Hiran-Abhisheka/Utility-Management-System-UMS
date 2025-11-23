package com.example.controller;

import com.example.entity.UtilityType;
import com.example.repository.UtilityTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-types")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilityTypeController {

    @Autowired
    private UtilityTypeRepository utilityTypeRepository;

    @GetMapping
    public List<UtilityType> getAllUtilityTypes() {
        return utilityTypeRepository.findAll();
    }

    @PostMapping
    public UtilityType createUtilityType(@RequestBody UtilityType utilityType) {
        return utilityTypeRepository.save(utilityType);
    }
}