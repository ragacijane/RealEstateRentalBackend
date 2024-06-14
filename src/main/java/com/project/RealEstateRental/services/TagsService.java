package com.project.RealEstateRental.services;

import com.project.RealEstateRental.exceptions.ResourceNotFoundException;
import com.project.RealEstateRental.models.Tags;
import com.project.RealEstateRental.repositories.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    private final TagsRepository tagsRepository;
    private final PropertyTagsService propertyTagsService;
    private final PropertiesService propertiesService;

    @Autowired
    public TagsService(TagsRepository tagsRepository, PropertyTagsService propertyTagsService, PropertiesService propertiesService) {
        this.tagsRepository = tagsRepository;
        this.propertyTagsService = propertyTagsService;
        this.propertiesService = propertiesService;
    }
    public List<Tags> getAllTags(){
        return tagsRepository.findAll();
    }
    public List<Integer> getPropertyTags(int id){
        return propertyTagsService.getTagsByProperty(propertiesService.getPropertyById(id));
    }
    public Tags getTagById(int id){
        return tagsRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("There is no tag with id: "+id)
        );
    }
}
