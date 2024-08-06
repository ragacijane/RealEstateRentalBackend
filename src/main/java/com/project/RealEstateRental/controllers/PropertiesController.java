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
    @GetMapping("/filteredProperties")
    public ResponseEntity<List<Properties>> filterProperties(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer structureId,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Integer squareFootage,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) String heating,
            @RequestParam(required = false) Integer equipmentId,
            @RequestParam(required = false) Integer boroughId,
            @RequestParam(required = false) String floor,
            @RequestParam(required = false) Integer active,
            @RequestParam(required = false) Integer visible,
            @RequestParam(required = false) Integer category,
            @RequestParam(required = false) Integer deposit,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tagIds) {
        return ResponseEntity.ok(propertiesService.getFilteredProperties(
                typeId,
                 structureId,
                 rooms,
                 squareFootage,
                 boroughId,
                 floor,
                 bathrooms,
                 heating,
                 equipmentId,
                 active,
                 visible,
                 category,
                 deposit,
                 price,
                 title,
                 description,
                 tagIds));
        }
}