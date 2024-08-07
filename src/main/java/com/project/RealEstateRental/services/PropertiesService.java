package com.project.RealEstateRental.services;

import com.project.RealEstateRental.requests.UpdateItemBody;
import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.OwnersRepository;
import com.project.RealEstateRental.repositories.PropertiesRepository;
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
    @Transactional
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
    @Transactional
    public void updateProperty(UpdateItemBody updateItemBody){
        Properties property = updateItemBody.getItem().getProperty();
        Owners owner = updateItemBody.getItem();

        propertiesRepository.save(property);

        ownersRepository.save(owner);

        propertyTagsService.deleteTagFromProperty(property);
        propertyTagsService.addTagsToProperty(updateItemBody.getTagIds(),property);
    }

    @Transactional
    public void updateThumbnailPhoto(Properties property,String newThumbnail){
        property.setThumbnail(newThumbnail);
        propertiesRepository.save(property);
    }
    public List<Properties> getFilteredProperties(
            Integer typeId,
            Integer structureId,
            Integer rooms,
            Integer squareFootage,
            Integer boroughId,
            String floor,
            Integer bathrooms,
            String heating,
            Integer equipmentId,
            Integer active,
            Integer visible,
            Integer category,
            Integer deposit,
            Integer price,
            String title,
            String description,
            String tagIds){
        Types type=constantsService.getTypeById(typeId);
        Structures structure = constantsService.getStructById(structureId);
        Boroughs borough = constantsService.getBoroughById(boroughId);
        Equipments equipment = constantsService.getEquipById(equipmentId);
        List<Integer> tagIdsList = propertyTagsService.parseTagIds(tagIds);
        Integer numTags = tagIdsList.size();
        return propertiesRepository.findByFilter(
                type, structure, rooms, squareFootage, bathrooms, heating,
                equipment, borough, floor, active, visible, category, deposit, price, title, description, tagIdsList,numTags);

    }
}
