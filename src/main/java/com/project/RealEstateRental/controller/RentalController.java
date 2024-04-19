package com.project.RealEstateRental.controller;

import com.project.RealEstateRental.model.Boroughs;
import com.project.RealEstateRental.repository.BoroughsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.RealEstateRental.repository.PropertiesRepository;

import java.util.List;

@RestController
public class RentalController {
    @Autowired
    PropertiesRepository propertiesRepository;
    @Autowired
    BoroughsRepository boroughsRepository;

    @GetMapping("/all")
    public List<Boroughs> getAlla(){
        return boroughsRepository.findAll();
    }
}
