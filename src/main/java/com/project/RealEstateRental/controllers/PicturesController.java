package com.project.RealEstateRental.controllers;

import com.project.RealEstateRental.models.Pictures;
import com.project.RealEstateRental.models.Properties;
import com.project.RealEstateRental.services.PicturesService;
import com.project.RealEstateRental.services.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/uploadPictures/{id}")
    @Transactional
    public ResponseEntity<MessageResponse> uploadImageToFileSystem(
            @PathVariable int id,
            MultipartFile[] images) throws IOException {
        if(images==null || images.length == 0)return ResponseEntity.badRequest().body(new MessageResponse("No files to upload!"));

        Properties property=propertiesService.getPropertyById(id);

        if(picturesService.uploadPicturesToProperty(images,property))
            return ResponseEntity.ok(new MessageResponse("Images uploaded SUCCESSFULLY!"));

        return ResponseEntity.badRequest().body(new MessageResponse("Images uploading FAILED!"));
    }
    @GetMapping("/getPictures/{id}")
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

    @GetMapping("/test")
    public List<String> test() {
        Properties property = propertiesService.getPropertyById(1);
        List<String> imagePaths = picturesService.testGetImages(property);

        // Prepend the URL path for the images
        String baseUrl = "http://localhost:8081/images/";
        return imagePaths.stream()
                .map(imagePath -> baseUrl + imagePath)
                .collect(Collectors.toList());
    }
}
