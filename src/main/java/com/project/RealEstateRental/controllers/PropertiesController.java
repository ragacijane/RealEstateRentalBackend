package com.project.RealEstateRental.controllers;
import com.project.RealEstateRental.dtos.PropertyProjection;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<PropertyProjection>> getAllProperties(){
        return ResponseEntity.ok(propertiesService.getFilteredProperties(null,null,null,null,null,null,null,null,null));
    }
    @GetMapping("/tags/{id}")
    public ResponseEntity<List<Integer>> getListOfTags(
            @PathVariable int id
    ){
        return ResponseEntity.ok(propertiesService.getPropertyTags(id));
    }

    @GetMapping("/property")
    public ResponseEntity<PropertyProjection> getSingleProperty(
            @RequestParam(required = true) Integer id
    ){
        PropertyProjection property = propertiesService.getProjectedById(id);

        // Check if the property is null or not visible
        if (property == null || property.getVisible() != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(property);
    }
    @GetMapping("/filters")
    public ResponseEntity<List<PropertyProjection>> filterProperties(
            @RequestParam(required = false) Integer idTy,
            @RequestParam(required = false) Integer idSt,
            @RequestParam(required = false) Integer sqMin,
            @RequestParam(required = false) Integer sqMax,
            @RequestParam(required = false) Integer idEq,
            @RequestParam(required = false) List<Integer> idBors,
            @RequestParam(required = false) Integer cat,
            @RequestParam(required = false) Integer prMin,
            @RequestParam(required = false) Integer prMax) {
        return ResponseEntity.ok(propertiesService.getFilteredProperties(
                idTy,
                 idSt,
                 sqMin,
                 sqMax,
                 idEq,
                 idBors,
                 cat,
                 prMin,
                 prMax));
        }
}