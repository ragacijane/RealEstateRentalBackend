package com.project.RealEstateRental.services;

import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.models.Property_tags;
import com.project.RealEstateRental.models.Tags;
import com.project.RealEstateRental.repositories.PropertyTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyTagsService {
    private final PropertyTagsRepository propertyTagsRepository;
    private final ConstantsService constantsService;
    @Autowired
    public PropertyTagsService(PropertyTagsRepository propertyTagsRepository, ConstantsService constantsService) {
        this.propertyTagsRepository = propertyTagsRepository;
        this.constantsService = constantsService;
    }

    public List<Integer> getTagsByProperty(Properties property){
        return propertyTagsRepository.findByProperty(property);
    }
    public void addTagsToProperty(String tagIdsString,Properties property){
        deleteTagFromProperty(property);
        List<Integer> tags=parseTagIds(tagIdsString);
        for(Integer temp:tags){
            Tags tag= constantsService.getTagById(temp);
            addTagToProperty(tag,property);
        }
    }

    public void deleteTagFromProperty(Properties property){
        propertyTagsRepository.deleteByProperty(property);
    }

    protected void addTagToProperty(Tags tag, Properties property){
        propertyTagsRepository.save(new Property_tags(property,tag));
    }
    public List<Integer> parseTagIds(String tagIdsString) {
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
