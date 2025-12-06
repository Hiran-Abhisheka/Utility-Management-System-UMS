package com.example.controller;

import com.example.entity.Tariff;
import com.example.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
@CrossOrigin(origins = "http://localhost:3000")
public class TariffController {

    @Autowired
    private TariffRepository tariffRepository;

    @GetMapping
    public List<Tariff> getAllTariffs() {
        return tariffRepository.findAll();
    }

    @PostMapping
    public Tariff createTariff(@RequestBody Tariff tariff) {
        return tariffRepository.save(tariff);
    }
}