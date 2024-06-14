package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.services.PicturesService;
import com.project.RealEstateRental.services.PropertiesService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pictures")
public class PicturesController {

    private final PicturesService picturesService;
    private final PropertiesService propertiesService;
    @Autowired
    public PicturesController(PicturesService picturesService, PropertiesService propertiesService) {
        this.picturesService = picturesService;
        this.propertiesService = propertiesService;
    }

    @PostMapping("/uploadImages/{id}")
    @Transactional
    public ResponseEntity<String> uploadImageToFileSystem(
            @PathVariable int id,
            MultipartFile[] images) throws IOException {
        if(images==null || images.length == 0)return ResponseEntity.badRequest().body("No files to upload!");

        Properties property=propertiesService.getPropertyById(id);

        if(picturesService.uploadPicturesToProperty(images,property))
            return ResponseEntity.ok("Images uploaded SUCCESSFULLY!");

        return ResponseEntity.badRequest().body("Images uploading FAILED!");
    }
    @GetMapping("/getImages/{id}")
    public ResponseEntity<List<PictureBody>> downloadImages(
            @PathVariable int id
    ){
        Properties property=propertiesService.getPropertyById(id);
        List<Pictures> existingPictures = picturesService.getPicturesByProperty(property);
        List<PictureBody> images = new ArrayList<>();
        if( existingPictures!=null ){
            for(Pictures picture: existingPictures) {
                images.add(new PictureBody(picture.getPictureName(), picture.getPicturePath()));
            }
        }
        return ResponseEntity.ok(images);
    }
}
