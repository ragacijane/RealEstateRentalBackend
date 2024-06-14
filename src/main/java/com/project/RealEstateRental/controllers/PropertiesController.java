package com.project.RealEstateRental.controllers;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/properties")

public class PropertiesController {
    private final PropertiesService propertiesService;
    @Autowired
    public PropertiesController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @PostMapping("/createProperty")
    public ResponseEntity<Owners> createProperty(
            @RequestBody PropertyBody propertyBody
    ){
        return ResponseEntity.ok(propertiesService.createProperty(propertyBody));
    }
    @PutMapping("/updateProperty/{id}")
    @Transactional
    public ResponseEntity<String> updateProperty(
            @RequestBody PropertyBody propertyBody,
            @PathVariable int id
    ){
        propertiesService.updateProperty(propertyBody,id);
        return ResponseEntity.ok("Property updated SUCCESSFULLY!");
    }
    @GetMapping("/getOwnsAndProps")
    public ResponseEntity<List<Owners>> getAllOwnersAndProperties(){
        return ResponseEntity.ok(
                propertiesService.getAllOwnersAndProperties()
        );
    }
    @GetMapping("/getProperties")
    public ResponseEntity<List<Properties>> getAllProperties(){
        return ResponseEntity.ok(propertiesService.getAllProperties());
    }
    @GetMapping("/getTags/{id}")
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