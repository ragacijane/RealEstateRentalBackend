package com.project.RealEstateRental.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class defaultController {

    public defaultController(){}
    @GetMapping("")
    public String def(){
        return "Its Live!";
    }
}
