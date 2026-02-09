package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.dtos.CustomerDTO;
import com.project.RealEstateRental.dtos.PropertyProjection;
import com.project.RealEstateRental.dtos.UpdateItemBody;
import com.project.RealEstateRental.models.Customers;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.services.CustomersService;
import com.project.RealEstateRental.services.PropertiesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final PropertiesService propertiesService;
    private final CustomersService customersService;
    @Autowired
    public AdminController(PropertiesService propertiesService, CustomersService customersService) {
        this.propertiesService = propertiesService;
        this.customersService = customersService;
    }
    @GetMapping("/properties")
    public ResponseEntity<List<Properties>> getAllOwnersAndProperties(){
        return ResponseEntity.ok(
                propertiesService.getAllProperties()
        );
    }
    @PostMapping("/properties")
    @Transactional
    public ResponseEntity<Long> createProperty(
            @RequestBody UpdateItemBody updateItemBody
    ){
        return ResponseEntity.ok(propertiesService.createProperty(updateItemBody));
    }
    @PutMapping("/properties")
    @Transactional
    public ResponseEntity<Boolean> updateProperty(
            @RequestBody UpdateItemBody updateItemBody
    ){
        return ResponseEntity.ok(propertiesService.updateProperty(updateItemBody));
    }
    @PostMapping("/properties/{id}/toggle-active")
    public ResponseEntity<String> toggleActive(@PathVariable Integer id) {
        try {
            propertiesService.toggleActiveField(id);
            return ResponseEntity.ok("Property active field toggled successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Property not found with id: " + id);
        }
    }
    @PostMapping("/properties/{id}/toggle-visible")
    public ResponseEntity<String> toggleVisible(@PathVariable Integer id) {
        try {
            propertiesService.toggleVisibleField(id);
            return ResponseEntity.ok("Property visible field toggled successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Property not found with id: " + id);
        }
    }
    @PostMapping("/customers")
    public ResponseEntity<Long> createCustomer(
            @RequestBody CustomerDTO customerBody
    ){
        return ResponseEntity.ok(customersService.createCustomer(customerBody));
    }

    @PutMapping("/customers")
    public ResponseEntity<Long> updateCustomer(
            @RequestBody CustomerDTO customerBody
    ){
        return ResponseEntity.ok(customersService.updateCustomer(customerBody));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(customersService.getCustomerById(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customers>> filterProperties(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size,
            @RequestParam(required = true) String sort,
            @RequestParam(required = true) boolean asc,
            @RequestParam(required = false) Integer idCustomer,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String secondName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String clientType) {
        return ResponseEntity.ok(customersService.getFilteredCustomers(
                page, size, sort, asc, idCustomer, firstName, secondName, email, phoneNumber, clientType));
    }
}
