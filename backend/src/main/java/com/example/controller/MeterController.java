package com.example.controller;

import com.example.entity.Meter;
import com.example.repository.MeterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meters")
@CrossOrigin(origins = "http://localhost:3000")
public class MeterController {

    @Autowired
    private MeterRepository meterRepository;

    @GetMapping
    public List<Meter> getAllMeters() {
        return meterRepository.findAll();
    }

    @PostMapping
    public Meter createMeter(@RequestBody Meter meter) {
        return meterRepository.save(meter);
    }
}