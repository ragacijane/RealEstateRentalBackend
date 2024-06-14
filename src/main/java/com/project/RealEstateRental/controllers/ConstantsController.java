package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.Boroughs;
import com.project.RealEstateRental.models.Equipments;
import com.project.RealEstateRental.models.Structures;
import com.project.RealEstateRental.models.Types;
import com.project.RealEstateRental.services.ConstantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class ConstantsController {
    private final ConstantsService constantsService;
    @Autowired
    public ConstantsController(ConstantsService constantsService) {
        this.constantsService = constantsService;
    }
    @GetMapping("/getBoroughs")
    public ResponseEntity<List<Boroughs>> getBoroughs(){

        return ResponseEntity.ok(constantsService.getBoroughs());
    }
    @GetMapping("/getStructures")
    public ResponseEntity<List<Structures>> getStructures(){

        return ResponseEntity.ok(constantsService.getStructures());
    }
    @GetMapping("/getTypes")
    public ResponseEntity<List<Types>> getTypes(){
        return ResponseEntity.ok(constantsService.getTypes());
    }
    @GetMapping("/getEquipments")
    public ResponseEntity<List<Equipments>> getEquipments(){
        return ResponseEntity.ok(constantsService.getEquipments());
    }
}
