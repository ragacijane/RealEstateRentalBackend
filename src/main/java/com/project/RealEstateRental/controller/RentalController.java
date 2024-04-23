package com.project.RealEstateRental.controller;

import com.project.RealEstateRental.model.*;
import com.project.RealEstateRental.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RentalController {
    @Autowired
    PropertiesRepository propertiesRepository;
    @Autowired
    OwnersRepository ownersRepository;
    @Autowired
    PropertyTagsRepository propertyTagsRepository;
    @Autowired
    BoroughsRepository boroughsRepository;
    @Autowired
    EquipmentsRepository equipmentsRepository;
    @Autowired
    PicturesRepository picturesRepository;
    @Autowired
    StructuresRepository structuresRepository;
    @Autowired
    TagsRepository tagsRepository;
    @Autowired
    TypesRepository typesRepository;
//int bathrooms, String heating, Equipments equipment, int status, int deposit, int price, String title, String description
    @PostMapping("/creatingProp")
    void create(
            @RequestBody CreatePropRequest propertyRequest
    ){
        Types type=typesRepository.findById(propertyRequest.getTypeId()).orElseThrow();
        Structures struct = structuresRepository.findById(propertyRequest.getStructureId()).orElseThrow();
        Boroughs borough = boroughsRepository.findById(propertyRequest.getBoroughId()).orElseThrow();
        Equipments equip = equipmentsRepository.findById(propertyRequest.getEquipmentId()).orElseThrow();

        Properties property = propertiesRepository.save(new Properties(
                    type,
                    struct,
                    propertyRequest.getRooms(),
                    propertyRequest.getSquareFootage(),
                    borough,
                    propertyRequest.getFloor(),
                    propertyRequest.getBathrooms(),
                    propertyRequest.getHeating(),
                    equip,
                    propertyRequest.getStatus(),
                    propertyRequest.getDeposit(),
                    propertyRequest.getPrice(),
                    propertyRequest.getTitle(),
                    propertyRequest.getDescription()
                )
        );
        Owners owner = ownersRepository.save(new Owners(
                propertyRequest.getFirstName(),
                propertyRequest.getLastName(),
                propertyRequest.getPhone(),
                propertyRequest.getContract(),
                propertyRequest.getStreet(),
                propertyRequest.getNumber(),
                property
        ));
    }
    @GetMapping("/test")
    public List<Property_tags> testaa(){
        return propertyTagsRepository.findAll();
    }
    @GetMapping("/properties")
    public List<Properties> filterProperties(
            @RequestParam(required = false) Integer typeId,
            @RequestParam(required = false) Integer structureId,
            @RequestParam(required = false) Integer rooms,
            @RequestParam(required = false) Integer squareFootage,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) String heating,
            @RequestParam(required = false) Integer equipmentId,
            @RequestParam(required = false) Integer boroughId,
            @RequestParam(required = false) Float floor,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer deposit,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tagIds) {

        List<Integer> tagIdsList = parseTagIds(tagIds);
        Integer numTags = tagIdsList.size();
        Types type = typeId != null ? typesRepository.findById(typeId).orElse(null) : null;
        Structures structure = structureId != null ? structuresRepository.findById(structureId).orElse(null) : null;
        Equipments equipment = equipmentId != null ? equipmentsRepository.findById(equipmentId).orElse(null) : null;
        Boroughs borough = boroughId != null ? boroughsRepository.findById(boroughId).orElse(null) : null;

        return propertiesRepository.findByFilter(
                type, structure, rooms, squareFootage, bathrooms, heating,
                equipment, borough, floor, status, deposit, price, title, description, tagIdsList,numTags);
    }

    private List<Integer> parseTagIds(String tagIdsString) {
        List<Integer> tagIds = new ArrayList<>();
        if (tagIdsString != null && !tagIdsString.isEmpty()) {
            String[] tagIdsArray = tagIdsString.split(",");
            for (String tagId : tagIdsArray) {
                tagIds.add(Integer.parseInt(tagId));
            }
        }
        return tagIds;
    }
}
