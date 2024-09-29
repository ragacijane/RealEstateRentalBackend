package com.project.RealEstateRental.services;

import com.project.RealEstateRental.requests.UpdateItemBody;
import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.OwnersRepository;
import com.project.RealEstateRental.repositories.PropertiesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropertiesService {
    private final PropertiesRepository propertiesRepository;
    private final OwnersRepository ownersRepository;
    private final ConstantsService constantsService;
    private final PropertyTagsService propertyTagsService;
    @Autowired
    public PropertiesService(PropertiesRepository propertiesRepository, OwnersRepository ownersRepository, ConstantsService constantsService, PropertyTagsService propertyTagsService) {
        this.propertiesRepository = propertiesRepository;
        this.ownersRepository = ownersRepository;
        this.constantsService = constantsService;
        this.propertyTagsService = propertyTagsService;
    }

    public Properties getPropertyById(int id){
        return propertiesRepository.findById(id)
                .orElseThrow(
                        ()->new ResourceNotFoundException("There is no property with id: "+id));
    }

    public List<Properties> getAllProperties(){
        return propertiesRepository.findAll();
    }
    public List<Owners> getAllOwnersAndProperties(){
        return ownersRepository.findAll();
    }
    public List<Integer> getPropertyTags(int id){
        return propertyTagsService.getTagsByProperty(getPropertyById(id));
    }

    public Owners createProperty(UpdateItemBody updateItemBody){
        Properties providedProperty=updateItemBody.getItem().getProperty();
        Properties property = propertiesRepository.save(new Properties(
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
        Owners providedOwner = updateItemBody.getItem();
        Owners owner = ownersRepository.save(new Owners(
                providedOwner.getName(),
                providedOwner.getEmail(),
                providedOwner.getPhone(),
                providedOwner.getContract(),
                providedOwner.getStreet(),
                providedOwner.getNumber(),
                providedOwner.getMoreInfo(),
                property
        ));

        propertyTagsService.addTagsToProperty(updateItemBody.getTagIds(),property);
        return owner;
    }

    public void updateProperty(UpdateItemBody updateItemBody){
        Properties property = updateItemBody.getItem().getProperty();
        Owners owner = updateItemBody.getItem();

        propertiesRepository.save(property);

        ownersRepository.save(owner);

        propertyTagsService.deleteTagFromProperty(property);
        propertyTagsService.addTagsToProperty(updateItemBody.getTagIds(),property);
    }

    public void updateThumbnailPhoto(Properties property,String newThumbnail){
        property.setThumbnail(newThumbnail);
        propertiesRepository.save(property);
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
    public List<Properties> getFilteredProperties(
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
