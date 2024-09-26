package com.project.RealEstateRental.controllers;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")

public class PropertiesController {
    private final PropertiesService propertiesService;
    @Autowired
    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @GetMapping("")
    public ResponseEntity<List<Properties>> getAllProperties(){
        return ResponseEntity.ok(propertiesService.getAllProperties());
    }
    @GetMapping("/tags/{id}")
    public ResponseEntity<List<Integer>> getListOfTags(
            @PathVariable int id
    ){
        return ResponseEntity.ok(propertiesService.getPropertyTags(id));
    }

    @GetMapping("/property")
    public ResponseEntity<Properties> getSingleProperty(
            @RequestParam(required = true) Integer id
    ){
        return ResponseEntity.ok(propertiesService.getPropertyById(id));
    }
    @GetMapping("/filters")
    public ResponseEntity<List<Properties>> filterProperties(
            @RequestParam(required = false) Integer idTy,
            @RequestParam(required = false) Integer idSt,
            @RequestParam(required = false) Integer sqMin,
            @RequestParam(required = false) Integer sqMax,
            @RequestParam(required = false) Integer idEq,
            @RequestParam(required = false) Integer idBor,
            @RequestParam(required = false) Integer cat,
            @RequestParam(required = false) Integer prMin,
            @RequestParam(required = false) Integer prMax) {
        return ResponseEntity.ok(propertiesService.getFilteredProperties(
                idTy,
                 idSt,
                 sqMin,
                 sqMax,
                 idEq,
                 idBor,
                 cat,
                 prMin,
                 prMax));
        }
}