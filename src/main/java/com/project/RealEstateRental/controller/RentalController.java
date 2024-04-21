package com.project.RealEstateRental.controller;

import com.project.RealEstateRental.model.*;
import com.project.RealEstateRental.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RentalController {
    @Autowired
    PropertiesRepository propertiesRepository;
    @Autowired
    BoroughsRepository boroughsRepository;
    @Autowired
    EquipmentsRepository equipmentsRepository;
    @Autowired
    PicturesRepository picturesRepository;
    @Autowired
    StructuresRepository structuresRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    TypesRepository typesRepository;
    @GetMapping("/all")
    public List<Boroughs> getAlla(){
        return boroughsRepository.findAll();
    }
}
