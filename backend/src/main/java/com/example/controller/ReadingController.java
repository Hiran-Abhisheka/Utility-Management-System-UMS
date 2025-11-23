package com.example.controller;

import com.example.entity.Reading;
import com.example.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/readings")
@CrossOrigin(origins = "http://localhost:3000")
public class ReadingController {

    @Autowired
    private ReadingRepository readingRepository;

    @GetMapping
    public List<Reading> getAllReadings() {
        return readingRepository.findAll();
    }

    @PostMapping
    public Reading createReading(@RequestBody Reading reading) {
        return readingRepository.save(reading);
    }
}