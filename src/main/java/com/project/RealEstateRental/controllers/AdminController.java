package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.Owners;
import com.project.RealEstateRental.requests.MessageResponse;
import com.project.RealEstateRental.requests.UpdateItemBody;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/properties")
public class AdminController {
    private final PropertiesService propertiesService;
    @Autowired
    public AdminController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }
    @GetMapping("")
    public ResponseEntity<List<Owners>> getAllOwnersAndProperties(){
        return ResponseEntity.ok(
                propertiesService.getAllOwnersAndProperties()
        );
    }
    @PostMapping("")
    public ResponseEntity<Owners> createProperty(
            @RequestBody UpdateItemBody updateItemBody
    ){
        return ResponseEntity.ok(propertiesService.createProperty(updateItemBody));
    }
    @PutMapping("")
    @Transactional
    public ResponseEntity<MessageResponse> updateProperty(
            @RequestBody UpdateItemBody updateItemBody
    ){
        propertiesService.updateProperty(updateItemBody);
        return ResponseEntity.ok(new MessageResponse("Property updated SUCCESSFULLY!"));
    }
}
