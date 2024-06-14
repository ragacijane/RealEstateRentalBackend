package com.project.RealEstateRental.controllers;
import com.project.RealEstateRental.models.Tags;
import com.project.RealEstateRental.services.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/tags")
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @GetMapping("/getTags")
    public ResponseEntity<List<Tags>> getTags(){
        return ResponseEntity.ok(tagsService.getAllTags());
    }
    @GetMapping("/getTags/from/{id}")
    public ResponseEntity<List<Integer>> getListOfTags(
            @PathVariable int id
    ){
        return ResponseEntity.ok(tagsService.getPropertyTags(id));
    }
}
