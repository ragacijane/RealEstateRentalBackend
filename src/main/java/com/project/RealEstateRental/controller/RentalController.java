package com.project.RealEstateRental.controller;

import com.project.RealEstateRental.model.*;
import com.project.RealEstateRental.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/properties")
    public List<Properties> filterProperties(
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long structureId,
            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer maxRooms,
            @RequestParam(required = false) Integer squareFootage,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) String heating,
            @RequestParam(required = false) Long equipmentId,
            @RequestParam(required = false) Long boroughId,
            @RequestParam(required = false) Float floor,
            @RequestParam(required = false) Integer status,
            @RequestParam(required = false) Integer deposit,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String tagIds) {

        List<Long> tagIdsList = parseTagIds(tagIds);
        long numTags = tagIdsList.size();
        Types type = typeId != null ? typesRepository.findById(Math.toIntExact(typeId)).orElse(null) : null;
        Structures structure = structureId != null ? structuresRepository.findById(Math.toIntExact(structureId)).orElse(null) : null;
        Equipments equipment = equipmentId != null ? equipmentsRepository.findById(Math.toIntExact(equipmentId)).orElse(null) : null;
        Boroughs borough = boroughId != null ? boroughsRepository.findById(Math.toIntExact(boroughId)).orElse(null) : null;

        return propertiesRepository.findByFilter(
                type, structure, minRooms, maxRooms, squareFootage, bathrooms, heating,
                equipment, borough, floor, status, deposit, price, title, description, tagIdsList,numTags);
    }

    private List<Long> parseTagIds(String tagIdsString) {
        List<Long> tagIds = new ArrayList<>();
        if (tagIdsString != null && !tagIdsString.isEmpty()) {
            String[] tagIdsArray = tagIdsString.split(",");
            for (String tagId : tagIdsArray) {
                tagIds.add(Long.parseLong(tagId));
            }
        }
        return tagIds;
    }
}
