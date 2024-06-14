package com.project.RealEstateRental.services;

import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.repositories.PropertyTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyTagsService {
    private final PropertyTagsRepository propertyTagsRepository;
    @Autowired
    public PropertyTagsService(PropertyTagsRepository propertyTagsRepository) {
        this.propertyTagsRepository = propertyTagsRepository;
    }

    public List<Integer> getTagsByProperty(Properties property){
        return propertyTagsRepository.findByProperty(property);
    }
}
