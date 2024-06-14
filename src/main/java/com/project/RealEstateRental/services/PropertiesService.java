package com.project.RealEstateRental.services;

import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
    private final PropertiesRepository propertiesRepository;
    @Autowired
    public PropertiesService(PropertiesRepository propertiesRepository) {
        this.propertiesRepository = propertiesRepository;
    }

    public Properties getPropertyById(int id){
        return propertiesRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("There is no property with id: "+id));
    }
}
