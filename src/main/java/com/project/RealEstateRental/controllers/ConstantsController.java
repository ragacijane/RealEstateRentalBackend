package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.services.ConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/constants")

public class ConstantsController {
    private final ConstantsService constantsService;
    @Autowired
    public ConstantsController(ConstantsService constantsService) {
        this.constantsService = constantsService;
    }
    @GetMapping("/boroughs")
    public ResponseEntity<List<Boroughs>> getBoroughs(){

        return ResponseEntity.ok(constantsService.getBoroughs());
    }
    @GetMapping("/structures")
    public ResponseEntity<List<Structures>> getStructures(){

        return ResponseEntity.ok(constantsService.getStructures());
    }
    @GetMapping("/types")
    public ResponseEntity<List<Types>> getTypes(){
        return ResponseEntity.ok(constantsService.getTypes());
    }
    @GetMapping("/equipments")
    public ResponseEntity<List<Equipments>> getEquipments(){
        return ResponseEntity.ok(constantsService.getEquipments());
    }
    @GetMapping("/tags")
    public ResponseEntity<List<Tags>> getTags(){
        return ResponseEntity.ok(constantsService.getAllTags());
    }
}
