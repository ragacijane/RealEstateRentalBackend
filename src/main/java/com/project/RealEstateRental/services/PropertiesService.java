package com.project.RealEstateRental.services;

import com.project.RealEstateRental.dtos.PropertyProjection;
import com.project.RealEstateRental.dtos.UpdateItemBody;
import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.PropertiesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertiesService {
    private final PropertiesRepository propertiesRepository;
    private final PropertyTagsService propertyTagsService;
    @Autowired
    public PropertiesService(PropertiesRepository propertiesRepository,ConstantsService constantsService, PropertyTagsService propertyTagsService) {
        this.propertiesRepository = propertiesRepository;
        this.propertyTagsService = propertyTagsService;
    }
    public PropertyProjection getProjectedById(int id){
        return propertiesRepository.findProjectedById(id);
    }
    public Properties getPropertyById(int id){
        return propertiesRepository.findById(id)
                .orElseThrow(
                        ()->new ResourceNotFoundException("There is no property with id: "+id));
    }

    public List<Properties> getAllProperties(){
        return propertiesRepository.findAll();
    }
    public List<Integer> getPropertyTags(int id){
        return propertyTagsService.getTagsByProperty(getPropertyById(id));
    }

    public synchronized long createProperty(UpdateItemBody updateItemBody){
        try{
            Properties providedProperty = updateItemBody.getItem();

            long nextId = Properties.getNextId(); // Get current nextId
            while (propertiesRepository.existsById((int) nextId)) {
                nextId++; // Increment nextId until a non-existing ID is found
            }
            Properties.setNextId(nextId);

            Properties property = propertiesRepository.save(new Properties(
                    providedProperty.getName(),
                    providedProperty.getEmail(),
                    providedProperty.getPhone(),
                    providedProperty.getContract(),
                    providedProperty.getStreet(),
                    providedProperty.getNumber(),
                    providedProperty.getMoreInfo(),
                    providedProperty.getType(),
                    providedProperty.getStructure(),
                    providedProperty.getRooms(),
                    providedProperty.getSquareFootage(),
                    providedProperty.getBorough(),
                    providedProperty.getFloor(),
                    providedProperty.getBathrooms(),
                    providedProperty.getHeating(),
                    providedProperty.getEquipment(),
                    providedProperty.getActive(),
                    providedProperty.getVisible(),
                    providedProperty.getCategory(),
                    providedProperty.getDeposit(),
                    providedProperty.getPrice(),
                    providedProperty.getTitle(),
                    providedProperty.getDescription()
            ));
            propertyTagsService.addTagsToProperty(updateItemBody.getTagIds(), property);
            return property.getIdProperty();
        }catch (Exception e){
            System.err.println("Error occurred while creating property: " + e.getMessage());
            return 0;
        }
    }

    public synchronized boolean updateProperty(UpdateItemBody updateItemBody){
        try{
            Properties property = updateItemBody.getItem();
            propertiesRepository.save(property);
            propertyTagsService.deleteTagFromProperty(property);
            propertyTagsService.addTagsToProperty(updateItemBody.getTagIds(), property);
            return true;
        }catch (Exception e){
            System.err.println("Error occurred while creating property: " + e.getMessage());
            return false;
        }
    }

    public boolean updateThumbnailPhoto(Properties property,String newThumbnail){
        try{
            property.setThumbnail(newThumbnail);
            propertiesRepository.save(property);
            return true;
        }
        catch(Exception e){
            System.err.println("Error occurred while creating property: " + e.getMessage());
            return false;
        }
    }

    public void toggleActiveField(Integer propertyId) {
        int rowsAffected = propertiesRepository.toggleActive(propertyId);
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Property not found with id: " + propertyId);
        }
    }

    public void toggleVisibleField(Integer propertyId) {
        int rowsAffected = propertiesRepository.toggleVisible(propertyId);
        if (rowsAffected == 0) {
            throw new EntityNotFoundException("Property not found with id: " + propertyId);
        }
    }
    public List<PropertyProjection> getFilteredProperties(
            Integer typeId,
            Integer structureId,
            Integer sqMin,
            Integer sqMax,
            Integer equipmentId,
            List<Integer> idBors,
            Integer cat,
            Integer prMin,
            Integer prMax){
//        List<Integer> tagIdsList = propertyTagsService.parseTagIds(tagIds);
//        Integer numTags = tagIdsList.size();
        return propertiesRepository.findByFilter(typeId,structureId,sqMin,sqMax,equipmentId,idBors,cat,prMin,prMax);
    }
}
