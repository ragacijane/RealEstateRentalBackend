package com.project.RealEstateRental.services;

import com.project.RealEstateRental.controllers.PropertyBody;
import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.*;
import com.project.RealEstateRental.repositories.OwnersRepository;
import com.project.RealEstateRental.repositories.PropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public Owners createProperty(PropertyBody propertyBody){
        Types type=constantsService.getTypeById(propertyBody.getTypeId());
        Structures struct = constantsService.getStructById(propertyBody.getStructureId());
        Boroughs borough = constantsService.getBoroughById(propertyBody.getBoroughId());
        Equipments equip = constantsService.getEquipById(propertyBody.getEquipmentId());

        Properties property = propertiesRepository.save(new Properties(
                type,
                struct,
                propertyBody.getRooms(),
                propertyBody.getSquareFootage(),
                borough,
                propertyBody.getFloor(),
                propertyBody.getBathrooms(),
                propertyBody.getHeating(),
                equip,
                propertyBody.getActive(),
                propertyBody.getVisible(),
                propertyBody.getCategory(),
                propertyBody.getDeposit(),
                propertyBody.getPrice(),
                propertyBody.getTitle(),
                propertyBody.getDescription()
        ));

        Owners owner = ownersRepository.save(new Owners(
                propertyBody.getName(),
                propertyBody.getEmail(),
                propertyBody.getPhone(),
                propertyBody.getContract(),
                propertyBody.getStreet(),
                propertyBody.getNumber(),
                propertyBody.getMoreInfo(),
                property
        ));

        propertyTagsService.addTagsToProperty(propertyBody.getTagIds(),property);
        return owner;
    }
    public void updateProperty(PropertyBody propertyBody, int id){
        Properties property = getPropertyById(id);
        Owners owner = ownersRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("There is no owner with id: "+id)
        );
        Types type=constantsService.getTypeById(propertyBody.getTypeId());
        Structures struct = constantsService.getStructById(propertyBody.getStructureId());
        Boroughs borough = constantsService.getBoroughById(propertyBody.getBoroughId());
        Equipments equip = constantsService.getEquipById(propertyBody.getEquipmentId());

        property.setType(type);
        property.setStructure(struct);
        property.setRooms(propertyBody.getRooms());
        property.setSquareFootage(propertyBody.getSquareFootage());
        property.setBorough(borough);
        property.setFloor(propertyBody.getFloor());
        property.setBathrooms(propertyBody.getBathrooms());
        property.setHeating(propertyBody.getHeating());
        property.setEquipment(equip);
        property.setActive(propertyBody.getActive());
        property.setDeposit(propertyBody.getDeposit());
        property.setPrice(propertyBody.getPrice());
        property.setTitle(propertyBody.getTitle());
        property.setDescription(propertyBody.getDescription());

        propertiesRepository.save(property);

        owner.setName(propertyBody.getName());
        owner.setEmail(propertyBody.getEmail());
        owner.setPhone(propertyBody.getPhone());
        owner.setContract(propertyBody.getContract());
        owner.setStreet(propertyBody.getStreet());
        owner.setNumber(propertyBody.getNumber());

        ownersRepository.save(owner);

        propertyTagsService.deleteTagFromProperty(property);
        propertyTagsService.addTagsToProperty(propertyBody.getTagIds(),property);
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
